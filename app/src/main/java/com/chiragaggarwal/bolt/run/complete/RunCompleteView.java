package com.chiragaggarwal.bolt.run.complete;

import com.chiragaggarwal.bolt.location.UserLocations;
import com.chiragaggarwal.bolt.timer.ElapsedTime;

public interface RunCompleteView {
    void saveRun(int ratingBarNumStars, String note, UserLocations userLocations, ElapsedTime elapsedTime);
}
