package com.chiragaggarwal.bolt.common;

import android.Manifest;
import android.content.pm.PackageManager;

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

    @Test
    public void testThatPermissionIsNotGrantedWhenNoGrantResultsArePresent() {
        String[] requestedPermissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        int[] grantResults = new int[]{};
        locationAwareBasePresenter.handleLocationPermissionResponse(LocationAwareBaseActivity.REQUEST_CODE_LOCATION, requestedPermissions, grantResults);
        Mockito.verify(locationAwareBaseView).showPermissionRequiredNotice();
    }

    @Test
    public void testThatItContinuesWithRequestingGpsToBeSeitchedOnAfterSuccessfullyGettingLocationPermission() {
        Mockito.when(locationApiStateMonitor.doesNotHaveLocationPermission()).thenReturn(false);
        Mockito.when(locationApiStateMonitor.isGpsNotEnabled()).thenReturn(true);
        String[] requestedPermissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        int[] grantResults = new int[]{PackageManager.PERMISSION_GRANTED, PackageManager.PERMISSION_GRANTED};
        locationAwareBasePresenter.handleLocationPermissionResponse(LocationAwareBaseActivity.REQUEST_CODE_LOCATION, requestedPermissions, grantResults);
        Mockito.verify(locationAwareBaseView, never()).showPermissionRequiredNotice();
        Mockito.verify(locationAwareBaseView).requestToEnableGps();
    }

    @Test
    public void testThatItContinuesWithFetchingLocationOnceAfterSuccessfullyGettingLocationPermission() {
        Mockito.when(locationApiStateMonitor.doesNotHaveLocationPermission()).thenReturn(false);
        Mockito.when(locationApiStateMonitor.isGpsNotEnabled()).thenReturn(false);
        String[] requestedPermissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        int[] grantResults = new int[]{PackageManager.PERMISSION_GRANTED, PackageManager.PERMISSION_GRANTED};
        locationAwareBasePresenter.handleLocationPermissionResponse(LocationAwareBaseActivity.REQUEST_CODE_LOCATION, requestedPermissions, grantResults);
        Mockito.verify(locationAwareBaseView, never()).showPermissionRequiredNotice();
        Mockito.verify(locationAwareBaseView).fetchLocationOnce();
    }

    @Test
    public void testThatItShowsLocationPermissionRequiredNoticeWhenPermissionDenied() {
        String[] requestedPermissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        int[] grantResults = new int[]{PackageManager.PERMISSION_DENIED, PackageManager.PERMISSION_DENIED};
        locationAwareBasePresenter.handleLocationPermissionResponse(LocationAwareBaseActivity.REQUEST_CODE_LOCATION, requestedPermissions, grantResults);
        Mockito.verify(locationAwareBaseView).showPermissionRequiredNotice();
    }

    @Test
    public void testThatItKnowsHowToHandleLocationPermissionResponseEvenIfUserHasRequestedForManyOtherPermissions() {
        Mockito.when(locationApiStateMonitor.doesNotHaveLocationPermission()).thenReturn(false);
        Mockito.when(locationApiStateMonitor.isGpsNotEnabled()).thenReturn(false);
        String[] requestedPermissions = new String[]{
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        int[] grantResults = new int[]{PackageManager.PERMISSION_DENIED, PackageManager.PERMISSION_GRANTED, PackageManager.PERMISSION_GRANTED};
        locationAwareBasePresenter.handleLocationPermissionResponse(LocationAwareBaseActivity.REQUEST_CODE_LOCATION, requestedPermissions, grantResults);
        Mockito.verify(locationAwareBaseView).fetchLocationOnce();
    }
}
