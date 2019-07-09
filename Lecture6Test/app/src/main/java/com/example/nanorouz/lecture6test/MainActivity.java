package com.example.nanorouz.lecture6test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText name;
    EditText age;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.edit1);
        age = findViewById(R.id.edit2);
        text = findViewById(R.id.text);
        if(savedInstanceState != null && savedInstanceState.containsKey("key")) {
            Log.d("Debug", savedInstanceState.getString("key"));
            text.setText(savedInstanceState.getString("key"));
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("key", "2");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        text.setText(savedInstanceState.getString("key"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sh = getSharedPreferences("MyOwnShared", MODE_PRIVATE);
        String str = sh.getString("user", "No name");
        int i = sh.getInt("age", -1);
        name.setText(str);
        age.setText(String.valueOf(i));

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sh = getSharedPreferences("MyOwnShared", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putString("user", name.getText().toString());
        myEdit.putInt("age", Integer.parseInt(age.getText().toString()));
        myEdit.commit();
        //,yEdit.apply();
    }

    protected void done(View view){
        finish();
    }
}
