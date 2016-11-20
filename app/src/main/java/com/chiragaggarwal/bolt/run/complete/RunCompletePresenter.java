package com.chiragaggarwal.bolt.run.complete;

import com.chiragaggarwal.bolt.analytics.FirebaseAnalyticsTracker;
import com.chiragaggarwal.bolt.analytics.RunEvent;
import com.chiragaggarwal.bolt.location.UserLocations;
import com.chiragaggarwal.bolt.timer.ElapsedTime;

public class RunCompletePresenter {
    private static final String REGEX_BLANK_STRING = "^\\s+$";
    private RunCompleteView runCompleteView;
    private FirebaseAnalyticsTracker firebaseAnalyticsTracker;

    public RunCompletePresenter(RunCompleteView runCompleteView, FirebaseAnalyticsTracker firebaseAnalyticsTracker) {
        this.runCompleteView = runCompleteView;
        this.firebaseAnalyticsTracker = firebaseAnalyticsTracker;
    }

    public void saveRun(int ratingBarNumStars, String note, UserLocations userLocations, ElapsedTime elapsedTime) {
        if (isNoteNotBlank(note)) {
            runCompleteView.saveRun(ratingBarNumStars, note, userLocations, elapsedTime);
            firebaseAnalyticsTracker.track(RunEvent.newCompleteRunEvent());
        }
    }

    private boolean isNoteNotBlank(String note) {
        return !note.matches(REGEX_BLANK_STRING);
    }
}
