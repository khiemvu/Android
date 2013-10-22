package com.qsoft.SQLite;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.Random;

public class MyActivity extends ListActivity {
    private StudentsDataSource datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        datasource = new StudentsDataSource(this);
        datasource.open();

        List<Student> students = datasource.getAllComments();

        // Use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Student> adapter = new ArrayAdapter<Student>(this,
                android.R.layout.simple_list_item_1, students);
        setListAdapter(adapter);
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        ArrayAdapter<Student> adapter = (ArrayAdapter<Student>) getListAdapter();
        Student student = null;
        switch (view.getId()) {
            case R.id.bt_add:
                String[] students = new String[] { "Nguyen Hoang Hai", "Trieu Ba Dong", "Vo Van Tong" };
                int nextInt = new Random().nextInt(3);
                // Save the new student to the database
                student = datasource.createComment(students[nextInt]);
                adapter.add(student);
                break;
            case R.id.bt_delete:
                if (getListAdapter().getCount() > 0) {
                    student = (Student) getListAdapter().getItem(0);
                    datasource.deleteComment(student);
                    adapter.remove(student);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

}