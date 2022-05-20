package com.jerico_victoria.midtermapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;

public class SplashScreen extends AppCompatActivity {
    Intent CallMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        CallMain = new Intent(".MainActivity");

        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    startActivity(CallMain);
                    finish();
                }
            }
        };
        timer.start();
    }
}