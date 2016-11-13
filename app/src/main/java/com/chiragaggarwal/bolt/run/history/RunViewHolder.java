package com.chiragaggarwal.bolt.run.history;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chiragaggarwal.bolt.R;

public class RunViewHolder extends RecyclerView.ViewHolder {
    private final TextView textDistanceTravelled;
    private final TextView textElapsedTime;
    private final TextView textDate;

    public RunViewHolder(View itemView) {
        super(itemView);
        textDistanceTravelled = (TextView) itemView.findViewById(R.id.text_distance_travelled);
        textElapsedTime = (TextView) itemView.findViewById(R.id.text_elapsed_time);
        textDate = (TextView) itemView.findViewById(R.id.text_date);
    }

    public void bind(String distanceTravelled, String elapsedTime, String date) {
        textDistanceTravelled.setText(distanceTravelled);
        textElapsedTime.setText(elapsedTime);
        textDate.setText(date);
    }
}
