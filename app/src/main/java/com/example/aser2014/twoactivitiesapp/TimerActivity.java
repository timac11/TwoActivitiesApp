package com.example.aser2014.twoactivitiesapp;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {

    private CountDownTimer timer;
    private final long interval = 1000;
    private final long allTime = 1001000;
    private int currentTime = 1;
    private Button button;
    private TextView textView;
    private final String START = "Start";
    private final String STOP = "Stop";
    private int currentUnit = 0;
    private int currentDozen = 0;
    private int currentHundread = 0;
    private String currentUnitString = "";
    private String currentDozenString = "";
    private String currentHundreadString = "";
    private String[] units;
    private String[] dozens;
    private String[] hundrets;

    private void createTimer(long allTime) {
        timer = new CountDownTimer(allTime, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                currentTime += 1;
                textView.setText(getCurrentNumber(currentTime));
            }

            @Override
            public void onFinish() {

            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        units = getResources().getStringArray(R.array.currentUnit);
        dozens = getResources().getStringArray(R.array.currentDozen);
        hundrets = getResources().getStringArray(R.array.currentHundred);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        View.OnClickListener oclButtonListener = createBtnListener();
        button.setOnClickListener(oclButtonListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        createTimer(allTime - currentTime * 1000);
        if (button.getText().equals(STOP)) {
            timer.start();
        } else timer.cancel();
    }

    private View.OnClickListener createBtnListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button.getText().equals(START)) {
                    if (timer != null) timer.start();
                    else {
                        createTimer(currentTime * 1000);
                        timer.start();
                    }
                    button.setText(STOP);
                } else {
                    timer.cancel();
                    button.setText(START);
                }
            }
        };
    }

    private String getCurrentNumber(int millsAfterStart) {
        //int currentSeconds = (int) millsAfterStart / 1000;
        currentUnit = millsAfterStart % 100;
        if (millsAfterStart <= 19) {
            return units[currentUnit];
        }
        if (millsAfterStart == 1000) {
            finishTimer();
            return getResources().getString(R.string.lastNum);
        }
        currentUnit = millsAfterStart % 100 % 10;
        currentDozen = (millsAfterStart / 10) % 10; // check it
        currentHundread = millsAfterStart / 100; // check it

        currentUnitString = units[currentUnit];
        currentDozenString = dozens[currentDozen];


        StringBuilder currentTime = new StringBuilder();

        if (currentHundread != 0) {
            currentHundreadString = hundrets[currentHundread];
            currentTime.append(currentHundreadString).append(" ");
            if (currentDozen > 1) {
                currentDozenString = dozens[currentDozen];
                currentTime.append(currentDozenString).append(" ");
            }
            if (millsAfterStart % 100 <= 19) {
                currentUnit = millsAfterStart % 100;
            }
            if (currentUnit != 0) {
                currentUnitString = units[currentUnit];
                currentTime.append(currentUnitString);
            }
            return currentTime.toString();
        } else if (currentDozen != 0) {
            currentDozenString = dozens[currentDozen];
            currentTime.append(currentDozenString).append(" ");
            if (currentUnit != 0) {
                currentUnitString = units[currentUnit];
                currentTime.append(currentUnitString);
            }
            return currentTime.toString();
        } else {
            currentUnitString = units[currentUnit];
            currentTime.append(currentUnitString);
            return currentTime.toString();
        }
    }

    protected void finishTimer() {
        if (timer != null) {
            timer.cancel();
            timer.onFinish();
        }
        currentTime = 1;
        button.setText(START);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(this.getString(R.string.currentTime), currentTime);
        outState.putString(this.getString(R.string.buttonState), (String) button.getText());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentTime = savedInstanceState.getInt(this.getString(R.string.currentTime));
        textView.setText(getCurrentNumber(currentTime));
        button.setText(savedInstanceState.getString(this.getString(R.string.buttonState)));
    }
}
