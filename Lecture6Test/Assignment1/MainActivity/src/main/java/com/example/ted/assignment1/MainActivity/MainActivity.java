package com.example.ted.assignment1.MainActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class  MainActivity extends AppCompatActivity {//} implements View.OnClickListener {
    Button button1;
    Button button2;
    Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.EnterInfo);
        button2 = (Button) findViewById(R.id.View);
        button3 = (Button) findViewById(R.id.Exit);
    }

    public void clickOne(View view) {
//        toastMessage("Enter photos was pressed");
        Intent intent = new Intent(MainActivity.this, enter_photo_info.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void clickTwo(View v) {
//        toastMessage("Viewing photos");
        Intent intent = new Intent(MainActivity.this, ListDBActivity.class);
        startActivity(intent);
    }

    public void clickThree(View v) {
        toastMessage("Goodbye!!!");
        finish();
        System.exit(0);
    }

    private void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
