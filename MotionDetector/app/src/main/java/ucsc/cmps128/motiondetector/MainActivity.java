package ucsc.cmps128.motiondetector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    Button exit_btn, clear_btn;
    MyReceiver my_receiver = new MyReceiver();
    private volatile long first_shake = -1;
    private final long thirty_secs = 3000;
    ReentrantLock lock = new ReentrantLock();
    private final String CLEAR_MSG = "Everything was quiet";
    private static final String PHONE_MOVED_BROADAST = "ucsc.cmps128.motiondetector";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter iff = new IntentFilter(PHONE_MOVED_BROADAST);
        LocalBroadcastManager.getInstance(this).registerReceiver(my_receiver, iff);

        tv = findViewById(R.id.notification_textview);
        tv.setText(CLEAR_MSG);
        clear_btn = findViewById(R.id.clear_button);
        exit_btn = findViewById(R.id.exit_button);

        runBackgroundThread();

        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this, SensorService.class));
                tv.setText(CLEAR_MSG);
                lock.lock();
                first_shake = -1;
                lock.unlock();
                runBackgroundThread();
            }
        });

        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this, SensorService.class));
                finish();
                System.exit(1);
            }
        });
    }


    private void runBackgroundThread() {
        Intent intent = new Intent(this, SensorService.class);
        startService(intent);
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(my_receiver);
        if (lock.isHeldByCurrentThread())
            lock.unlock();
        super.onPause();
    }

    @Override
    protected void onResume() {
        IntentFilter iff = new IntentFilter(PHONE_MOVED_BROADAST);
        LocalBroadcastManager.getInstance(this).registerReceiver(my_receiver, iff);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(my_receiver);
        super.onDestroy();
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context ctx, Intent i) {
            lock.lock();
            if (first_shake == -1) {
                first_shake = System.currentTimeMillis();
            }
            lock.unlock();
            final Handler handler = new Handler();
            Timer timer = new Timer(false);
            TimerTask changeUI = new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            lock.lock();
                            if (first_shake != -1 && System.currentTimeMillis() - first_shake >= thirty_secs)
                                tv.setText("The phone moved!");
                            lock.unlock();
                        }
                    });
                }
            };
            timer.schedule(changeUI, thirty_secs);
        }
    }


}
