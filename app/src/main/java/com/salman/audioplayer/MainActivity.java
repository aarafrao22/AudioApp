package com.salman.audioplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("AudioPlayer");
        setSupportActionBar(toolbar);


        ConstraintLayout img1 = findViewById(R.id.img1);
        CardView cardFazilat = findViewById(R.id.cardFazilat);
        CardView ruq = findViewById(R.id.ruqaia);
        CardView dua_e_qanoot = findViewById(R.id.dua_e_qanoot);
        CardView azan = findViewById(R.id.azan);
        CardView surah_e_fat = findViewById(R.id.surah_e_fat);

        img1.setOnClickListener(this);
        cardFazilat.setOnClickListener(this);
        ruq.setOnClickListener(this);
        dua_e_qanoot.setOnClickListener(this);
        azan.setOnClickListener(this);
        surah_e_fat.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img1:
                Intent intent = new Intent(getApplicationContext(), AudioActivity.class);
                intent.putExtra("status", 1);
                startActivity(intent);
                break;
            case R.id.azan:
                Intent intent2 = new Intent(getApplicationContext(), AudioActivity.class);
                intent2.putExtra("status", 3);
                startActivity(intent2);
                break;
            case R.id.dua_e_qanoot:
                Intent intent3 = new Intent(getApplicationContext(), AudioActivity.class);
                intent3.putExtra("status", 4);
                startActivity(intent3);
                break;
            case R.id.ruqaia:
                Intent intent4 = new Intent(getApplicationContext(), AudioActivity.class);
                intent4.putExtra("status", 5);
                startActivity(intent4);
                break;
            case R.id.surah_e_fat:
                Intent intent5 = new Intent(getApplicationContext(), AudioActivity.class);
                intent5.putExtra("status", 2);
                startActivity(intent5);
                break;

            case R.id.cardFazilat:
                Intent intent1 = new Intent(getApplicationContext(), FazilatActivity.class);
                startActivity(intent1);
                break;
        }
    }
}