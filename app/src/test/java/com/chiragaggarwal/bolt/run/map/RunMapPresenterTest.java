package com.chiragaggarwal.bolt.run.map;

import com.chiragaggarwal.bolt.location.NullUserLocation;
import com.chiragaggarwal.bolt.location.UserLocation;

import org.junit.Test;
import org.mockito.Mockito;

public class RunMapPresenterTest {
    @Test
    public void testThatItPlotsPolylineForUserlocationWhichExists() {
        RunMapView view = Mockito.mock(RunMapView.class);
        UserLocation lastUserLocation = new UserLocation(0.2D, 4.3D, true, 6F, true, 22F);
        UserLocation currentUserLocation = new UserLocation(1.2D, 2.3D, true, 5F, true, 10F);
        new RunMapPresenter(view).extendPolyline(lastUserLocation, currentUserLocation);
        Mockito.verify(view).plotPolyline(lastUserLocation.toLatLng(), currentUserLocation.toLatLng());
    }

    @Test
    public void testThatItDoesNotPlotPolylineForUserlocationWhichDoesNotExist() {
        RunMapView view = Mockito.mock(RunMapView.class);
        UserLocation lastUserLocation = new NullUserLocation();
        UserLocation currentUserLocation = new UserLocation(1.2D, 2.3D, true, 5F, true, 10F);
        new RunMapPresenter(view).extendPolyline(lastUserLocation, currentUserLocation);
        Mockito.verify(view, Mockito.never()).plotPolyline(lastUserLocation.toLatLng(), currentUserLocation.toLatLng());
    }
}