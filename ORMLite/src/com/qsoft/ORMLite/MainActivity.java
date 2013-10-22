package com.qsoft.ORMLite;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener
{
    EditText etName;
    EditText etAge;
    ListView listView;
    StudentAdapter adapter = null;
    DatabaseHelper helper;
    List<Student> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        listView = (ListView) findViewById(R.id.lvShowResult);
        listView.setOnItemClickListener(this);

        etName = (EditText) findViewById(R.id.etentry);
        etAge = (EditText) findViewById(R.id.etage);

        helper = new DatabaseHelper(getApplicationContext());

        setDataToAdapter();

    }

    public void addData(View v) {
        String strName = "Name: " +etName.getText().toString().trim();
        String strAge = "Age: " + etAge.getText().toString().trim();
        if (TextUtils.isEmpty(etName.getText().toString().trim()) && TextUtils.isEmpty(etAge.getText().toString().trim())) {
            showToast("Name and Age is compulsory !!!");
            return;
        }

        Student person = new Student();
        person.setName(strName);
        person.setAge(strAge);

        helper.addData(person);

        showToast("Data Successfully Added");

        setDataToAdapter();

    }

    private void setDataToAdapter() {

        list = helper.GetData();

        adapter = new StudentAdapter(this, R.layout.row, list);
        listView.setAdapter(adapter);

    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void deleteData(View v) {

        list = helper.GetData();

        if (null != list && list.size() > 0) {
            AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
                    .create();
            alert.setTitle("Delete ?");
            alert.setMessage("Are you sure want to delete All data from Database");

            alert.setButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.setButton2("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    helper.deleteAll();
                    etName.setText("");
                    etAge.setText("");
                    showToast("Removed All Data!!!");

                    setDataToAdapter();
                }
            });
            alert.show();
        } else {
            showToast("No data found from the Database");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        showToast(list.get(position).getName());

    }
}
