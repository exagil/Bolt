package com.chiragaggarwal.bolt.run.history;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chiragaggarwal.bolt.R;
import com.chiragaggarwal.bolt.run.Run;
import com.chiragaggarwal.bolt.run.RunViewModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class RunDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private Run run;
    private TextView textDetailDistance;
    private TextView textDetailTime;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_details);
        run = getIntent().getParcelableExtra(Run.TAG);
        initialiseView();
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    private void initialiseView() {
        mapView = (MapView) findViewById(R.id.map_view);
        textDetailTime = (TextView) findViewById(R.id.text_detail_time);
        textDetailDistance = (TextView) findViewById(R.id.text_detail_distance);
        ratingBar = (RatingBar) findViewById(R.id.run_rating);
        textDetailDistance.setText(run.formattedTotalDistanceInKilometers());
        RunViewModel runViewModel = new RunViewModel(getResources());
        runViewModel.setElapsedTime(run.elapsedTimeInSeconds);
        textDetailTime.setText(runViewModel.getElapsedTime());
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        mapView.onSaveInstanceState(bundle);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        initialise(googleMap);
        List<LatLng> travelledPoints = run.getTravelledPoints();
        if (travelledPoints.size() >= 2) {
            plotPolyline(googleMap, travelledPoints);
            animateToPolyline(googleMap, run.getTravelledBounds());
        }
    }

    private void initialise(GoogleMap googleMap) {
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setAllGesturesEnabled(false);
        uiSettings.setZoomControlsEnabled(false);
    }

    private void plotPolyline(GoogleMap googleMap, List<LatLng> travelledPoints) {
        PolylineOptions runRoutePolylineOptions = buildRunPolylineOptions(travelledPoints);
        plotSourceMarker(googleMap, travelledPoints);
        plotDestinationMarker(googleMap, travelledPoints);
        googleMap.addPolyline(runRoutePolylineOptions);
    }

    private void plotSourceMarker(GoogleMap googleMap, List<LatLng> travelledPoints) {
        LatLng startPoint = travelledPoints.get(0);
        plotMarker(googleMap, startPoint, R.drawable.marker_source);
    }

    private void plotDestinationMarker(GoogleMap googleMap, List<LatLng> travelledPoints) {
        LatLng lastPoint = travelledPoints.get(travelledPoints.size() - 1);
        plotMarker(googleMap, lastPoint, R.drawable.marker_destination);
    }

    private void plotMarker(GoogleMap googleMap, LatLng startPoint, int marker_source) {
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(marker_source);
        MarkerOptions markerOptions = new MarkerOptions().position(startPoint).visible(true).icon(bitmapDescriptor);
        googleMap.addMarker(markerOptions);
    }

    private void animateToPolyline(GoogleMap googleMap, LatLngBounds travelledBounds) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(
                travelledBounds,
                displayMetrics().widthPixels,
                getResources().getDimensionPixelSize(R.dimen.width_run_details_map),
                getResources().getDimensionPixelSize(R.dimen.bolt_dimen_large)
        );
        googleMap.animateCamera(cameraUpdate);
    }

    @NonNull
    private PolylineOptions buildRunPolylineOptions(List<LatLng> travelledPoints) {
        PolylineOptions runRoutePolylineOptions = new PolylineOptions();
        runRoutePolylineOptions.addAll(travelledPoints);
        runRoutePolylineOptions.color(getResources().getColor(R.color.polyline_color));
        runRoutePolylineOptions.width(getResources().getDimensionPixelSize(R.dimen.polyline_width));
        return runRoutePolylineOptions;
    }

    @NonNull
    private DisplayMetrics displayMetrics() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }
}
