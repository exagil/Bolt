package com.chiragaggarwal.bolt.run;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.chiragaggarwal.bolt.R;

public class RunInProgressNotification {
    private Context context;
    private RunViewModel runViewModel;

    public RunInProgressNotification(Context context, RunViewModel runViewModel) {
        this.context = context;
        this.runViewModel = runViewModel;
    }

    public Notification build() {
        return new NotificationCompat.Builder(context).
                setSmallIcon(R.drawable.ic_stat_man_sprinting).
                setColor(context.getResources().getColor(R.color.colorPrimary)).
                setContentTitle(context.getString(R.string.app_name)).
                setContentText(context.getString(R.string.run_in_progress_notification_text)).
                setSubText(runViewModel.getNotificationSubText()).
                build();
    }
}
