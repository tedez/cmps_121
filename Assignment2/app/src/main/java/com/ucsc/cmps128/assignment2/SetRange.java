package com.ucsc.cmps128.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SetRange extends AppCompatActivity {
    EditText et1, et2;
    Button btn;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_range_activity);
        et1 = findViewById(R.id.range_s_view);
        et2 = findViewById(R.id.range_f_view);
        btn = findViewById(R.id.display_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rangeData_nav();
            }
        });
    }

    private void rangeData_nav() {
        Intent i = new Intent(this, RangeData.class);
        Bundle b = new Bundle();
        b.putInt("start", Integer.parseInt(et1.getText().toString()));
        b.putInt("finish", Integer.parseInt(et2.getText().toString()));
        i.putExtras(b);
        startActivity(i);
        finish();
    }
}
