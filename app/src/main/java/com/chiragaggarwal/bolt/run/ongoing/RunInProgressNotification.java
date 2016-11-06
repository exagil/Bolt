package com.chiragaggarwal.bolt.run.ongoing;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.chiragaggarwal.bolt.R;
import com.chiragaggarwal.bolt.run.RunViewModel;

public class RunInProgressNotification {
    private static final int REQUEST_CODE_OPEN_RUN_ACTIVITY = 0;
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
                setColor(context.getResources().getColor(R.color.colorPrimary))
                .setContentIntent(buildIntent());
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle().
                setBigContentTitle(notificationTitle).
                addLine(context.getString(R.string.run_in_progress_notification_text)).
                addLine(runViewModel.getNotificationElapsedTime()).
                addLine(runViewModel.getNotificationDistance());
        return notificationBuilder.setStyle(inboxStyle).build();
    }

    private PendingIntent buildIntent() {
        Intent intent = new Intent(context, RunActivity.class);
        return PendingIntent.getActivity(context, REQUEST_CODE_OPEN_RUN_ACTIVITY, intent, PendingIntent.FLAG_ONE_SHOT);
    }
}
