package com.example.nanorouz.lecture5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        textView = findViewById(R.id.textView2);
        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        textView.setText(name);
        Intent i = new Intent();
        i.putExtra("Info", "We successfully visited page 2");
        setResult(RESULT_OK, i);
    }
}
