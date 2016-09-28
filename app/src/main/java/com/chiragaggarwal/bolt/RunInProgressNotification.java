package com.chiragaggarwal.bolt;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

public class RunInProgressNotification {
    private Context context;
    private RunViewModel runViewModel;

    public RunInProgressNotification(Context context, RunViewModel runViewModel) {
        this.context = context;
        this.runViewModel = runViewModel;
    }

    public Notification build() {
        return new NotificationCompat.Builder(context).
                setSmallIcon(R.mipmap.ic_launcher).
                setContentTitle(context.getString(R.string.app_name)).
                setContentText(context.getString(R.string.run_in_progress_notification_text)).
                setSubText(String.format(context.getString(R.string.format_elapsed_time), runViewModel.getElapsedTime())).
                build();
    }
}
