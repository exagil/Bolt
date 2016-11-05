package com.chiragaggarwal.bolt.run.complete;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chiragaggarwal.bolt.R;
import com.chiragaggarwal.bolt.run.RunViewModel;
import com.chiragaggarwal.bolt.timer.ElapsedTime;

public class RunCompleteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_complete);
        ElapsedTime elapsedTime = getIntent().getParcelableExtra(ElapsedTime.TAG);
        RunViewModel runViewModel = new RunViewModel(getResources());
        runViewModel.setElapsedTime(elapsedTime);
    }
}
