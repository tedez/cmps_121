package com.example.nanorouz.mybroadcastreceiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void callInBuilt(View view) {

    }

    public void customBroadcast(View view) {
        Log.i("broadcast", "called");
        Intent i1 = new Intent();
        i1.setAction("com.cmps121.demo");
        //i1.addCategory("android.intent.category.DEFAULT");
        sendBroadcast(i1);
    }
}
