package com.example.ted.assignment1.MainActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListDBActivity extends AppCompatActivity {
    private static final String TAG = "ListDBActivity";
    DBHelper db_helper = new DBHelper(this);
    private ListView my_listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listdb);
        my_listView = (ListView)findViewById(R.id.list_view);

        populateListView();
       // finish();
    }

    private void populateListView() {
        Cursor data = db_helper.getData();
        ArrayList<String> list = new ArrayList<String>();
        while (data.moveToNext()) {
            list.add(data.getString(1) + "\n" + data.getString(2) + "\n" + data.getString(3));
        }
        if (list.size() > 0) {
            ListAdapter adapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
            my_listView.setAdapter(adapt);
        } else {
            toastMessage("Nothing to view...");
            finish();
        }
    }


    public void onPause(View v) {
        Intent i = new Intent(ListDBActivity.this, MainActivity.class);
        setResult(RESULT_OK, i);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
