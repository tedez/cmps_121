package com.ucsc.cmps128.assignment2;
import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import static okhttp3.MediaType.parse;


public class DownloadData extends AppCompatActivity {
    public static ImageView iv;
    public String url, title;
    DownloadImage di;
    EditText url_et, title_et;
    MyDB db;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_activity);

        Button dl = (Button)findViewById(R.id.download_button);
        url_et = (EditText)findViewById(R.id.url_textview);
        title_et = (EditText)findViewById(R.id.title_textview);
        iv = findViewById(R.id.image);
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        db = new MyDB(this);//, "pictures_db", null, 1);

    }

    public void downloadImage(View v) {
        if(networkInfo != null && networkInfo.isConnected()) {
            url = url_et.getText().toString();
            title = title_et.getText().toString();
            // create async thread DownloadImage and attempt to download picture
            di = new DownloadImage();
            di.execute(url, title);
        } else { toastMessage("Connection unavailable") ; }
        finish();
    }

    private void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                 ASYNC DOWNLOAD IMAGES CLASS
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        private String count, title;
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                title = strings[1];
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setReadTimeout(1000);
                con.setConnectTimeout(1000);
                con.setRequestMethod("GET");
                con.connect();

                InputStream is = con.getInputStream();
                Bitmap image = BitmapFactory.decodeStream(is);
                return image;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            toastMessage("No internet Connection");
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //SaveImage(bitmap);
            String abs_path = "";
            if (isExternalStorageWritable() && isSDCardAvailable() && checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // /storage/self/primary/Pictures
                File mydir = getPublicAlbumStorageDir("/CMPS128-Asg2");
                abs_path = mydir + title + ".jpg";
                File file = new File (mydir, title + ".jpg");
                Uri p = null;

                if (file.exists ())
                    file.delete ();
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, title, null);
                    p = Uri.parse(path);
                    out.flush();
                    out.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                Uri.parse("file://" + file)));
            } else {
                toastMessage("Cannot write to external storage...");
            }

            long result = db.addDataPath(abs_path , title);
            if (result == -1) {
                toastMessage("Failed to add to db");
            } else {
                toastMessage("Successfully added to db");
            }

//            finish();
        }
/*
 https://www.nbqsa.org/wp-content/uploads/2014/08/UCSC-Logo-Better.jpg
*/

    }

    private boolean checkPermission(String permission) {
        return (ContextCompat.checkSelfPermission(this, permission) ==
                PackageManager.PERMISSION_GRANTED);
    }

    private boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public File getPublicAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("mkdir", "Directory not created");
        }
        return file;
    }
}





