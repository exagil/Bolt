package com.chiragaggarwal.bolt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RunActivity extends AppCompatActivity {
    @BindView(R.id.button_start_activity)
    TextView textStartActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_main);
    }

    @OnClick(R.id.button_start_activity)
    public void onTextStartActivityClick() {
        Intent runServiceIntent = new Intent(this, RunService.class);
        startService(runServiceIntent);
    }
}
