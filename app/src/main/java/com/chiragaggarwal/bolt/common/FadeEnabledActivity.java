package com.chiragaggarwal.bolt.common;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;

public abstract class FadeEnabledActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialiseTransitions();
    }

    protected void initialiseTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(new Fade(Fade.OUT).setDuration(300));
            getWindow().setEnterTransition(new Fade(Fade.OUT).setDuration(300));
        }
    }

    @Override
    public void startActivity(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            super.startActivity(intent);
        }
    }
}
