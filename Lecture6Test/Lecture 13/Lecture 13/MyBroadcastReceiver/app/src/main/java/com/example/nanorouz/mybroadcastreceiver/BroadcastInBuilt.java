package com.example.nanorouz.mybroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BroadcastInBuilt extends BroadcastReceiver {

    public BroadcastInBuilt(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Battery is low!", Toast.LENGTH_SHORT).show();
    }
}
