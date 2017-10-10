package com.example.aser2014.twoactivitiesapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private CountDownTimer timer;
    private final long interval = 1000;
    private long allTime = 2000;
    private int currentState = 0;

    private void createTimer() {
        timer = new CountDownTimer(allTime, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                currentState++;
                if (currentState == 2) {
                    Intent intent = new Intent(MainActivity.this, TimerActivity.class);
                    MainActivity.this.finish();
                    startActivity(intent);
                }
            }

            @Override
            public void onFinish() {
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createTimer();
    }

    protected void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(this.getString(R.string.currentTime1), currentState);
        super.onSaveInstanceState(outState);
    }


    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentState = savedInstanceState.getInt(this.getString(R.string.currentTime1));
    }
}
