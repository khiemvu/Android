package com.qsoft.ORMLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * User: khiemvx
 * Date: 10/22/13
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper
{
    private Context _context;
    private static final String DATABASE_NAME = "student.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Student, String> simpleDao = null;
    private RuntimeExceptionDao<Student, String> simpleRuntimeDao = null;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        _context = context;
    }

    public Dao<Student, String> getDao() throws SQLException
    {
        if(simpleDao == null)
        {
            simpleDao = getDao(Student.class);
        }
        return simpleDao;
    }
    public RuntimeExceptionDao<Student, String> getSimpleDataDao(){
        if(simpleRuntimeDao == null){
            simpleRuntimeDao = getRuntimeExceptionDao(Student.class);
        }
        return simpleRuntimeDao;
    }
    //method for get all rows
    public List<Student> GetData(){
        DatabaseHelper helper = new DatabaseHelper(_context);
        RuntimeExceptionDao<Student,String> simpleDao = helper.getSimpleDataDao();
        List<Student> studentList = simpleDao.queryForAll();
        return studentList;
    }
    //method for add a row
    public int addData(Student student)
    {
        RuntimeExceptionDao<Student, String> dao = getSimpleDataDao();
        int i = dao.create(student);
        return i;
    }
    //method for delete all rows
    public void deleteAll()
    {
        RuntimeExceptionDao<Student, String> dao = getSimpleDataDao();
        List<Student> list = dao.queryForAll();
        dao.delete(list);
    }

    @Override
    public void close() {
        super.close();
        simpleRuntimeDao = null;
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Student.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Student.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }
}
