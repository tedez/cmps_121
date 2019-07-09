package com.ucsc.cmps128.assignment2;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RangeData extends AppCompatActivity {
    ListView listView;
    MyDB db;
    int s,f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.range_activity);
        listView = findViewById(R.id.range_list_view);
        db = new MyDB(this);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            s = b.getInt("start");
            f = b.getInt("finish");
        }
        populateListView(s, f);
    }

    private void populateListView(int start, int finish) {
        Cursor c = db.getRange(start, finish);

        List<Node> entries = new ArrayList<>();
        while (c.moveToNext()) {
            Node node = new Node();
            byte[] image = c.getBlob(2);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            node.title = c.getString(1);
            node.image = bitmap;
            node.primary_isd = c.getString(0);
            entries.add(node);
        }
        if (entries.size() > 0) {
            CustomAdapter adapter = new CustomAdapter(this, R.layout.list_node, entries);
            listView.setAdapter(adapter);
        } else {
            toastMessage("Input range is invalid...\ncheck view for id range..");
            finish();
        }
    }
    private void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

}