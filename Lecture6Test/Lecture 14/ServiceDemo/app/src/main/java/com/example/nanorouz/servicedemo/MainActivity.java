package com.example.nanorouz.servicedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start(View view) {
        Intent i1 = new Intent(this, MyService.class);
        startService(i1);
    }

    public void end(View view) {
        Intent i1 = new Intent(this, MyService.class);
        stopService(i1);
    }
}
