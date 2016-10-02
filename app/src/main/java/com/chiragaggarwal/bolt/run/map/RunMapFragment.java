package com.chiragaggarwal.bolt.run.map;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chiragaggarwal.bolt.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RunMapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap googleMap;

    @BindView(R.id.map_view)
    public MapView mapView;

    @Override
    public final void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public final void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public final void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public final void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public final void onSaveInstanceState(Bundle var1) {
        super.onSaveInstanceState(var1);
        mapView.onSaveInstanceState(var1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_run, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    @SuppressWarnings("MissingPermission")
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMyLocationEnabled(true);
    }
}
