package com.chiragaggarwal.bolt.run.complete;

import com.chiragaggarwal.bolt.location.UserLocations;
import com.chiragaggarwal.bolt.timer.ElapsedTime;

public class RunCompletePresenter {
    private static final String REGEX_BLANK_STRING = "^\\s+$";
    private RunCompleteView runCompleteView;

    public RunCompletePresenter(RunCompleteView runCompleteView) {

        this.runCompleteView = runCompleteView;
    }

    public void saveRun(int ratingBarNumStars, String note, UserLocations userLocations, ElapsedTime elapsedTime) {
        if (isNoteNotBlank(note)) {
            runCompleteView.saveRun(ratingBarNumStars, note, userLocations, elapsedTime);
        }
    }

    private boolean isNoteNotBlank(String note) {
        return !note.matches(REGEX_BLANK_STRING);
    }
}
