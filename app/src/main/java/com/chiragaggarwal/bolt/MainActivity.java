package com.chiragaggarwal.bolt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TimerView {
    private Intent service;
    private TextView textTime;
    private ActivityTimer activityTimer;
    private Button buttonToggleTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        service = new Intent(this, LocationService.class);
        textTime = (TextView) findViewById(R.id.timer);
        buttonToggleTimer = (Button) findViewById(R.id.button_start);
        buttonToggleTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityTimer.start();
                startService(service);
            }
        });
    }

    @Override
    protected void onStop() {
        activityTimer.stop();
        stopService(service);
        super.onStop();
    }

    @Override
    public void onTick(String hours, String minutes, String seconds) {
        textTime.setText(hours + " : " + minutes + " : " + seconds);
    }
}
