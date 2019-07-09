package com.example.nanorouz.lecture10test;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends Activity { //implements SensorEventListener {

    private boolean moved, good_to_spy;
    private int move_count = 0;
    private Button clear_btn, exit_btn;
    public TextView notification_tv;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private String clear_msg = "Everything was quiet";
    private String moved_msg = "The phone moved!";

    private float deltaX = 0;
    private float deltaY = 0;
    private float deltaZ = 0;

    private float vibrateThreshold = 2;
    private Date date = new Date();
    private long time;

    public Vibrator v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     //   time = date.getSeconds();
  //      toastMessage(Long.toString(time));
        good_to_spy = true;
        exit_btn = findViewById(R.id.exit_button);
        clear_btn = findViewById(R.id.clear_button);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        notification_tv = findViewById(R.id.notif_textview);
        notification_tv.setText(clear_msg);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
         //   vibrateThreshold = accelerometer.getMaximumRange() / 10;
        } else {
            // fail! we don't have an accelerometer!
        }

        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move_count = 0;
                notification_tv.setText(clear_msg);
            }
        });

        //initialize vibration
        v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

    }

    //onResume() register the accelerometer for listening the events
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //onPause() unregister the accelerometer for stop listening the events
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // get the change of the x and y values of the accelerometer
        deltaX = Math.abs(0 - event.values[0]);
        deltaY = Math.abs(0 - event.values[1]);

        // if the change is below 2, it is just plain noise
        deltaX = (deltaX < 2 ? 0 : deltaX);
        deltaY = (deltaY < 2 ? 0 : deltaY);
        deltaZ = (deltaZ < 2 ? 0 : deltaZ);
        if (deltaX  > vibrateThreshold || (deltaY > vibrateThreshold) || deltaZ > vibrateThreshold) {
            move_count++;
            if (move_count > 1 && good_to_spy)
                notification_tv.setText(moved_msg);
        }
    }

    private class Creep extends AsyncTaskimplements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            // get the change of the x and y values of the accelerometer
            deltaX = Math.abs(0 - event.values[0]);
            deltaY = Math.abs(0 - event.values[1]);

            // if the change is below 2, it is just plain noise
            deltaX = (deltaX < 2 ? 0 : deltaX);
            deltaY = (deltaY < 2 ? 0 : deltaY);
            deltaZ = (deltaZ < 2 ? 0 : deltaZ);
            if (deltaX  > vibrateThreshold || (deltaY > vibrateThreshold) || deltaZ > vibrateThreshold) {
                move_count++;
                if (move_count > 1 && good_to_spy)
                    notification_tv.setText(moved_msg);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
    private void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
