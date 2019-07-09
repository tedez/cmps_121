package com.ucsc.cmps128.assignment2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class DeleteData extends AppCompatActivity {
    EditText et;
    Button ok_b;
    MyDB db;
    CheckBox id,title;
    Spinner sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_activity);
        et = findViewById(R.id.delete_textview);
        ok_b = findViewById(R.id.ok_button);
        db = new MyDB(this);
        sp = findViewById(R.id.spinner);
        populateSpinner();
        ok_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove();
            }
        });
    }

    private void populateSpinner() {
        String[] derp = {"ID", "title"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, derp);
        sp.setAdapter(adapter);
    }
    private void remove(){
        Integer deleted;
        String y = sp.getItemAtPosition(sp.getSelectedItemPosition()).toString();
        String x = et.getText().toString();
        deleted = db.deleteKey(x,y);

        if (deleted == 0) {
            toastMessage(y + ": "+ x + " not in db...");
        } else {
         //   toastMessage("Deleted " + y);
        }
    }
    private void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

}

