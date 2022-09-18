package com.salman.audioplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class FazilatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fazilat);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Fazilat");
        setSupportActionBar(toolbar);
    }
}