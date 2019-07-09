package com.example.ted.assignment1.MainActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class enter_photo_info extends AppCompatActivity {
    private Spinner spinner;
    DBHelper   myDBHelper;
    private EditText name, photographer;
    private Spinner  year;
    public Button   save;
    private int CURRENT_YEAR = 2018;
    private int CAMERA_INVENTION_DATE = 1816;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_photo_info);
       // getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        configureView();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabData(v);
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        ((EditText)findViewById(R.id.photo_name)).getText().clear();
        ((EditText)findViewById(R.id.photographer)).getText().clear();
    }

    private void grabData(View v) {
        String n = name.getText().toString();
        String p = photographer.getText().toString();
        String y = year.getItemAtPosition(year.getSelectedItemPosition()).toString();
        if (name.length() == 0 || photographer.length() == 0) {
            toastMessage("All fields were not entered...");
        } else {
            addData(n, p, y);
        }
    }

    private void configureView() {
        populateSpinner();
        Intent i = getIntent();
        name = (EditText)findViewById(R.id.photo_name);
        photographer = (EditText)findViewById(R.id.photographer);
        year = (Spinner)(findViewById(R.id.spinner));
        myDBHelper = new DBHelper(this);
        save = (Button)findViewById(R.id.done_button);
    }
    public void addData(String name, String photographer, String year) {
        boolean inserted = myDBHelper.addData(name, photographer, year);
        if (inserted) { ; } else { toastMessage("Failed to enter into db...");}
    }

    public void onPause(View v) {
        Intent i = new Intent(enter_photo_info.this, MainActivity.class);
        setResult(RESULT_OK, i);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void populateSpinner() {
        spinner = (Spinner)findViewById(R.id.spinner);
        List<Integer> years = new ArrayList<Integer>();
        for (int i = CURRENT_YEAR; i > CAMERA_INVENTION_DATE; --i)
            years.add(i);
        ArrayAdapter<Integer> adapt = new ArrayAdapter<Integer>(this, android.R.layout.simple_dropdown_item_1line, years);
        spinner.setAdapter(adapt);
    }

    private void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

}
