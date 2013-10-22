package com.qsoft.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class StudentsDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_NAME};

	public StudentsDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Student createComment(String comment) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_NAME, comment);
		long insertId = database.insert(MySQLiteHelper.TABLE_STUDENT, null,
				values);
		// To show how to query
		Cursor cursor = database.query(MySQLiteHelper.TABLE_STUDENT,
				allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		return cursorToComment(cursor);
	}

	public void deleteComment(Student student) {
		long id = student.getId();
		System.out.println("Student deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_STUDENT, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<Student> getAllComments() {
		List<Student> students = new ArrayList<Student>();
		Cursor cursor = database.query(MySQLiteHelper.TABLE_STUDENT,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Student student = cursorToComment(cursor);
			students.add(student);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return students;
	}

	private Student cursorToComment(Cursor cursor) {
		Student student = new Student();
		student.setId(cursor.getLong(0));
		student.setName(cursor.getString(1));
		return student;
	}
}
