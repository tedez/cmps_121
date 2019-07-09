package ucsc.cmps128.motiondetector;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;


public class SensorService extends Service implements SensorEventListener {
    private float deltaX, deltaY;
    private int thirty_secs = 3000;
    boolean good_to_spy, first_accel;
    float threshold;
    private long start_time;
    Sensor accelero;
    SensorManager manager;
    private static final String PHONE_MOVED_BROADCAST = "ucsc.cmps128.motiondetector";
    PowerManager pm;
    PowerManager.WakeLock wl;
    @Override
    public void onCreate() {
        super.onCreate();
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        threshold = 2;
        start_time = System.currentTimeMillis();
        first_accel = false;


        if ((accelero = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)) != null)
            manager.registerListener(this, accelero, SensorManager.SENSOR_DELAY_NORMAL);

        pm = (PowerManager)getSystemService(POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "motiondetector:wakelock");
        wl.acquire(100);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        long now = System.currentTimeMillis();
        good_to_spy = (now - start_time) >= thirty_secs;
        if (good_to_spy) {
            // get the change of the x and y values of the accelerometer
            deltaX = Math.abs(0 - event.values[0]);
            deltaY = Math.abs(0 - event.values[1]);

            // if the change is below 2, it is just plain noise
            deltaX = (deltaX < 2 ? 0 : deltaX);
            deltaY = (deltaY < 2 ? 0 : deltaY);

            if (deltaX > threshold || (deltaY > threshold)) {
                Intent i = new Intent(PHONE_MOVED_BROADCAST);
                LocalBroadcastManager.getInstance(this).sendBroadcast(i);
            }
        } else {
         //   toastMessage("why do I need to toast something??");
        }
    }

    @Override
    public void onDestroy() {
        wl.release();
        manager.unregisterListener(this);
        super.onDestroy();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
//    private void toastMessage(String msg) {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//    }
}
