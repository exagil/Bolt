package com.chiragaggarwal.bolt.common;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.never;

public class LocationAwareBasePresenterTest {
    private LocationAwareBasePresenter locationAwareBasePresenter;
    private LocationApiStateMonitor locationApiStateMonitor;
    private LocationAwareBaseView locationAwareBaseView;

    @Before
    public void setUp() throws Exception {
        locationApiStateMonitor = Mockito.mock(LocationApiStateMonitor.class);
        locationAwareBaseView = Mockito.mock(LocationAwareBaseView.class);
        locationAwareBasePresenter = new LocationAwareBasePresenter(locationAwareBaseView, locationApiStateMonitor);
    }

    @Test
    public void testThatLocationIsFetchedWhenUserHasLocationPermissionAndGpsIsEnabled() {
        Mockito.when(locationApiStateMonitor.doesNotHaveLocationPermission()).thenReturn(false);
        Mockito.when(locationApiStateMonitor.isGpsNotEnabled()).thenReturn(false);
        locationAwareBasePresenter.updateLocation();
        Mockito.verify(locationAwareBaseView).fetchLocationOnce();
    }

    @Test
    public void testThatLocationPermissionIsRequestedWhenTriedToUpdateLocationAndPermissionNotAvailable() {
        Mockito.when(locationApiStateMonitor.doesNotHaveLocationPermission()).thenReturn(true);
        Mockito.when(locationApiStateMonitor.isGpsNotEnabled()).thenReturn(false);
        locationAwareBasePresenter.updateLocation();
        Mockito.verify(locationAwareBaseView).requestLocationPermission();
        Mockito.verify(locationAwareBaseView, never()).fetchLocationOnce();
    }

    @Test
    public void testThatRequestToEnableGpsIsRaisedWhenGpsIsNotEnabled() {
        Mockito.when(locationApiStateMonitor.doesNotHaveLocationPermission()).thenReturn(false);
        Mockito.when(locationApiStateMonitor.isGpsNotEnabled()).thenReturn(true);
        locationAwareBasePresenter.updateLocation();
        Mockito.verify(locationAwareBaseView).requestToEnableGps();
        Mockito.verify(locationAwareBaseView, never()).fetchLocationOnce();
    }

    @Test
    public void testThatLocationPermissionIsRequestedWhenLocationPermissionIsNotPresentAndGpsIsNotEnabled() {
        Mockito.when(locationApiStateMonitor.doesNotHaveLocationPermission()).thenReturn(true);
        Mockito.when(locationApiStateMonitor.isGpsNotEnabled()).thenReturn(true);
        locationAwareBasePresenter.updateLocation();
        Mockito.verify(locationAwareBaseView).requestLocationPermission();
        Mockito.verify(locationAwareBaseView, never()).requestToEnableGps();
        Mockito.verify(locationAwareBaseView, never()).fetchLocationOnce();
    }
}
