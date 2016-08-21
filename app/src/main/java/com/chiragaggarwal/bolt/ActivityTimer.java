package com.chiragaggarwal.bolt;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityTimer extends Timer implements Handler.Callback {
    private int elaspedSeconds = 0;
    private final Handler timeHandler;
    private TimerView timerView;

    public ActivityTimer(TimerView timerView) {
        this.timerView = timerView;
        timeHandler = new Handler(this);
    }

    public void start() {
        super.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                elaspedSeconds += 1;
                int numberOfHours = elaspedSeconds / 3600;
                int numberOfMinutes = (elaspedSeconds - (numberOfHours * 3600)) / 60;
                int numberOfSeconds = elaspedSeconds - (numberOfHours * 3600) - (numberOfMinutes * 60);
                String hours = String.format(Locale.ENGLISH, "%2d", numberOfHours);
                String minutes = String.format(Locale.ENGLISH, "%2d", numberOfMinutes);
                String seconds = String.format(Locale.ENGLISH, "%2d", numberOfSeconds);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("hours", hours);
                bundle.putString("minutes", minutes);
                bundle.putString("seconds", seconds);
                message.setData(bundle);
                timeHandler.sendMessage(message);
            }
        }, 0l, 1000l);
    }

    public void stop() {
        elaspedSeconds = 0;
        timeHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public boolean handleMessage(Message msg) {
        Bundle data = msg.getData();
        String hours = data.getString("hours");
        String minutes = data.getString("minutes");
        String seconds = data.getString("seconds");
        timerView.onTick(hours, minutes, seconds);
        return false;
    }
}
