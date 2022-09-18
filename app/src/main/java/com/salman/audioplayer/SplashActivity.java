package com.salman.audioplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import java.lang.reflect.Method;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    private Method method;
    private static final int SPLASH_SCREEN_TIME_OUT = 2000;


    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this,
                        MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);

    }
}