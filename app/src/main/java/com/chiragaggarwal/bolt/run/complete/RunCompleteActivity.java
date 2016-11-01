package com.chiragaggarwal.bolt.run.complete;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chiragaggarwal.bolt.R;
import com.chiragaggarwal.bolt.location.UserLocations;

public class RunCompleteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_complete);
        UserLocations userLocations = getIntent().getParcelableExtra(UserLocations.TAG);
    }
}
