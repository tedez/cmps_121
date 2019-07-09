package com.ucsc.cmps128.assignment2;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ViewData extends AppCompatActivity {
    ListView listView;
    MyDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity);
        listView = findViewById(R.id.list_view);
        db = new MyDB(this);
        populateListView();
    }

    private void populateListView() {
        Cursor c = db.getAll();
        List<Node> entries = new ArrayList<>();
        String path;
        while(c.moveToNext()) {
            Node node = new Node();
            path = c.getString(3);
            File image = new File(path);
            if (image.exists()) {
                node.image = BitmapFactory.decodeFile(image.getAbsolutePath());
            }

//            byte[] image = c.getBlob(2);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

            node.title = c.getString(1);
//            node.image = bitmap;
            node.primary_isd = c.getString(0);

            entries.add(node);
        }
        if (entries.size() > 0) {
            CustomAdapter adapter = new CustomAdapter(this, R.layout.list_node, entries);
            listView.setAdapter(adapter);
        } else {
            toastMessage("DB is empty, download an image!");
            finish();
        }
    }
    private void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
