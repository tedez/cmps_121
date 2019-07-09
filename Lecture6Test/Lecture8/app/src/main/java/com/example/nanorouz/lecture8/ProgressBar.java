package com.example.nanorouz.lecture8;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by nanorouz on 10/25/18.
 */

public class ProgressBar extends AsyncTask<Void, Integer, String> {
    ProgressDialog pd;
    Context ctx;

    public ProgressBar(Context context){
        ctx = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(ctx);
        pd.setMax(10);
        pd.setMessage("Please wait");
        pd.setTitle("Counting ...");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setButton(ProgressDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancel(true);
                pd.dismiss();
            }
        });
        pd.show();
    }

    @Override
    protected String doInBackground(Void... voids) {

        //Log.i("Thread", "Thread Created");
        for(int i = 1; i <= 10; i++){
            try {
                Thread.sleep(1000);
                publishProgress(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return "Success";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int val = values[0];
        pd.setProgress(val);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
        pd.dismiss();
    }
}
