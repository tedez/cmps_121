package com.example.nanorouz.lecture8;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ProgressBar pb;
    InternetConnection ic;
    DownloadImage di;

    static TextView textView;
    static ImageView imageView;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.image);

        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
    }

    public void count(View view) {
        pb = new ProgressBar(this);
        pb.execute();
    }

    public void web(View view) {
        ic = new InternetConnection();
        ic.execute("http://google.com");
    }

    public void downloadImage(View view) {
        if(networkInfo != null && networkInfo.isConnected()){
           di = new DownloadImage();
           di.execute("https://www.nbqsa.org/wp-content/uploads/2014/08/UCSC-Logo-Better.jpg");
        }
        else
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
    }
}
