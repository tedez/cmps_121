package com.ucsc.cmps128.assignment2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button download_b, delete_b, view_b, range_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
/// Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
        download_b = (Button) findViewById(R.id.download_button);
        delete_b = (Button) findViewById(R.id.delete_button);
        view_b= (Button) findViewById(R.id.view_button);
        range_b = (Button) findViewById(R.id.range_button);

        download_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadNav();
            }
        });

        delete_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNav();
            }
        });

        view_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewNav();
            }
        });

        range_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rangeNav();
            }
        });
    }

    private void downloadNav() {
        Intent i = new Intent(this, DownloadData.class);
        startActivity(i);
    }
    private void deleteNav() {
        Intent i = new Intent(this, DeleteData.class);
        startActivity(i);
    }
    private void viewNav() {
        Intent i = new Intent(this, ViewData.class);
        startActivity(i);
    }
    private void rangeNav() {
        Intent i = new Intent(this, SetRange.class);
        startActivity(i);
    }

}
