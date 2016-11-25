package com.chiragaggarwal.bolt.run.ongoing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;

import com.chiragaggarwal.bolt.BoltApplication;
import com.chiragaggarwal.bolt.R;
import com.chiragaggarwal.bolt.analytics.FirebaseAnalyticsTracker;
import com.chiragaggarwal.bolt.common.LocationAwareBaseActivity;
import com.chiragaggarwal.bolt.common.ServiceStateMonitor;
import com.chiragaggarwal.bolt.databinding.ActivityMainBinding;
import com.chiragaggarwal.bolt.location.UserLocation;
import com.chiragaggarwal.bolt.location.UserLocations;
import com.chiragaggarwal.bolt.run.RunViewModel;
import com.chiragaggarwal.bolt.run.complete.RunCompleteActivity;
import com.chiragaggarwal.bolt.run.map.RunMapFragment;
import com.chiragaggarwal.bolt.timer.ElapsedTime;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RunActivity extends LocationAwareBaseActivity implements RunView {
    private RunServiceBroadcastReceiver runServiceBroadcastReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private RunViewModel runViewModel;
    private RunPresenter runPresenter;

    private ActionBarDrawerToggle drawerToggle;

    @BindView(R.id.frame_map)
    public View frameMap;

    @BindView(R.id.main_toolbar)
    public Toolbar toolbar;

    @BindView(R.id.drawer_main)
    public DrawerLayout drawerMain;

    @Inject
    public ServiceStateMonitor serviceStateMonitor;

    @Inject
    public FirebaseAnalyticsTracker firebaseAnalyticsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ButterKnife.bind(this);
        ((BoltApplication) getApplication()).getBoltComponent().inject(this);
        initialise(activityMainBinding);
    }

    @Override
    protected void initialiseTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Slide(Gravity.RIGHT).setDuration(300));
            getWindow().setExitTransition(new Slide(Gravity.LEFT).setDuration(300));
        }
    }

    @Override
    public void onOneOffLocationUpdate(UserLocation userLocation) {
        runMapFragment().updateLocation(userLocation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        runPresenter.init();
        IntentFilter runServiceIntentFilter = new IntentFilter();
        runServiceIntentFilter.addAction(RunService.ACTION_TIME_TICK);
        runServiceIntentFilter.addAction(RunService.ACTION_FETCH_ACCURATE_LOCATION);
        localBroadcastManager.registerReceiver(runServiceBroadcastReceiver, runServiceIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        localBroadcastManager.unregisterReceiver(runServiceBroadcastReceiver);
    }

    @Override
    public void startRun() {
        startService(runServiceIntent());
    }

    @Override
    public void stopRun() {
        stopService(runServiceIntent());
        runMapFragment().clearMap();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void showRunCompleteScreen() {
        Intent intent = new Intent(this, RunCompleteActivity.class);
        intent.putExtra(UserLocations.TAG, runViewModel.userLocations);
        intent.putExtra(ElapsedTime.TAG, runViewModel.elapsedTime);
        startActivity(intent);
    }

    private class RunServiceBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(RunService.ACTION_TIME_TICK)) {
                ElapsedTime elapsedTime = intent.getParcelableExtra(ElapsedTime.TAG);
                runViewModel.setElapsedTime(elapsedTime);
            } else if (action.equals(RunService.ACTION_FETCH_ACCURATE_LOCATION)) {
                UserLocations userLocations = intent.getParcelableExtra(UserLocations.TAG);
                runViewModel.updateVisitedUserLocations(userLocations);
                runMapFragment().updateLocations(userLocations);
            }
        }
    }

    private void initialise(ActivityMainBinding activityMainBinding) {
        setupToolbar();
        setupNavigationDrawer();
        runViewModel = new RunViewModel(getResources());
        runPresenter = new RunPresenter(this, runViewModel, serviceStateMonitor, firebaseAnalyticsTracker);
        activityMainBinding.setRunViewModel(runViewModel);
        activityMainBinding.setRunPresenter(runPresenter);
        runServiceBroadcastReceiver = new RunServiceBroadcastReceiver();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        getFragmentManager().beginTransaction().add(R.id.frame_map, new RunMapFragment()).commit();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupNavigationDrawer() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerMain, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerToggle.setHomeAsUpIndicator(R.drawable.common_full_open_on_phone);
        drawerMain.setDrawerListener(drawerToggle);
    }

    @NonNull
    private Intent runServiceIntent() {
        return new Intent(this, RunService.class);
    }

    private RunMapFragment runMapFragment() {
        return (RunMapFragment) getFragmentManager().findFragmentById(R.id.frame_map);
    }
}
