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
        String notificationTitle = context.getString(R.string.app_name);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context).
                setContentTitle(notificationTitle).
                setContentText(runViewModel.getNotificationSubText()).
                setPriority(NotificationCompat.PRIORITY_MAX).
                setCategory(NotificationCompat.CATEGORY_STATUS).
                setSmallIcon(R.drawable.ic_stat_man_sprinting).
                setColor(context.getResources().getColor(R.color.colorPrimary));
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle().
                setBigContentTitle(notificationTitle).
                addLine(context.getString(R.string.run_in_progress_notification_text)).
                addLine(runViewModel.getNotificationElapsedTime()).
                addLine(runViewModel.getNotificationDistance());
        return notificationBuilder.setStyle(inboxStyle).build();
    }
}
