package com.example.nanorouz.lecture4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView1;
    TextView textview2;
    Button button1;
    Button button2;
    Button button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = (TextView) findViewById(R.id.text1);
        textview2 = (TextView)findViewById(R.id.text2);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textview2.setText("Button 2 is pressed!");
                Log.d("button", "2");
            }
        });
        button3.setOnClickListener(this);
    }

    public void clickOne(View view) {
        textView1.setText("Button 1 is pressed!");
        Log.d("button", "1");

    }

    @Override
    public void onClick(View v) {
        Toast toast = Toast.makeText(this, "Button 3 is pressed", Toast.LENGTH_LONG);
        toast.show();
        Log.d("button", "3");


    }
}
