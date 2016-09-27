package com.chiragaggarwal.bolt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RunActivity extends AppCompatActivity {
    private RunServiceBroadcastReceiver runServiceBroadcastReceiver;

    @BindView(R.id.button_start_activity)
    TextView textStartActivity;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        runServiceBroadcastReceiver = new RunServiceBroadcastReceiver();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter runServiceIntentFilter = new IntentFilter();
        runServiceIntentFilter.addAction(RunService.ACTION_TIME_TICK);
        localBroadcastManager.registerReceiver(runServiceBroadcastReceiver, runServiceIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        localBroadcastManager.unregisterReceiver(runServiceBroadcastReceiver);
    }

    @OnClick(R.id.button_start_activity)
    public void onTextStartActivityClick() {
        Intent runServiceIntent = new Intent(this, RunService.class);
        startService(runServiceIntent);
    }

    private class RunServiceBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
        }
    }
}
