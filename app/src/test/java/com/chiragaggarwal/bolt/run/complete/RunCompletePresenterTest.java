package com.chiragaggarwal.bolt.run.complete;

import com.chiragaggarwal.bolt.analytics.FirebaseAnalyticsTracker;
import com.chiragaggarwal.bolt.location.UserLocations;
import com.chiragaggarwal.bolt.timer.ElapsedTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import static org.mockito.Mockito.never;

public class RunCompletePresenterTest {
    private FirebaseAnalyticsTracker firebaseAnalyticsTracker;

    @Before
    public void setUp() throws Exception {
        firebaseAnalyticsTracker = Mockito.mock(FirebaseAnalyticsTracker.class);
        Mockito.doAnswer(invocation -> null).when(firebaseAnalyticsTracker).track(Matchers.any());
    }

    @Test
    public void testThatRunIsNeverSavedWhenNoteIsBlankWithWhiteSpaces() {
        int ratingBarNumStars = 3;
        String note = "     ";
        UserLocations userLocations = new UserLocations();
        ElapsedTime elapsedTime = new ElapsedTime();
        RunCompleteView runCompleteView = Mockito.mock(RunCompleteView.class);
        RunCompletePresenter runCompletePresenter = new RunCompletePresenter(runCompleteView, firebaseAnalyticsTracker);
        runCompletePresenter.saveRun(ratingBarNumStars, note, userLocations, elapsedTime);
        Mockito.verify(runCompleteView, never()).saveRun(Matchers.anyInt(), Matchers.anyString(), Matchers.any(), Matchers.any());
    }

    @Test
    public void testThatRunIsSavedWhenNoteIsBlankWithoutWhiteSpaces() {
        int ratingBarNumStars = 3;
        String note = "";
        UserLocations userLocations = new UserLocations();
        ElapsedTime elapsedTime = new ElapsedTime();
        RunCompleteView runCompleteView = Mockito.mock(RunCompleteView.class);
        RunCompletePresenter runCompletePresenter = new RunCompletePresenter(runCompleteView, firebaseAnalyticsTracker);
        runCompletePresenter.saveRun(ratingBarNumStars, note, userLocations, elapsedTime);
        Mockito.verify(runCompleteView).saveRun(ratingBarNumStars, note, userLocations, elapsedTime);
    }

    @Test
    public void testThatRunIsSavedWhenNoteIsNotBlank() {
        int ratingBarNumStars = 3;
        String note = "abcd1234";
        UserLocations userLocations = new UserLocations();
        ElapsedTime elapsedTime = new ElapsedTime();
        RunCompleteView runCompleteView = Mockito.mock(RunCompleteView.class);
        RunCompletePresenter runCompletePresenter = new RunCompletePresenter(runCompleteView, firebaseAnalyticsTracker);
        runCompletePresenter.saveRun(ratingBarNumStars, note, userLocations, elapsedTime);
        Mockito.verify(runCompleteView).saveRun(ratingBarNumStars, note, userLocations, elapsedTime);
    }
}
