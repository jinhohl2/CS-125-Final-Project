package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

public class MapActivity extends AppCompatActivity {

    private boolean hasLocationPermission;

    private static final String TAG = "MapActivity";

    private boolean centeredMap;

    private BroadcastReceiver broadcastReceiver;

    private static final float REQUIRED_LOCATION_ACCURACY = 28f;

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.gameMap);
        mapFragment.getMapAsync(view -> {

            // Save the newly obtained map
            map = view;
            setUpMap();
        });


        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                Location location = intent.getParcelableExtra(LocationListener.UPDATE_DATA_ID);
                if (map != null && location != null && location.hasAccuracy()
                        && location.getAccuracy() < REQUIRED_LOCATION_ACCURACY) {
                    markMyPosition(location);
                    ensureMapCentered(location);
                }
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(LocationListener.UPDATE_ACTION));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // We don't have it yet - request it
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            Log.v(TAG, "Requested location permission");
        } else {
            // We do have it - activate the features that require location
            Log.v(TAG, "Already had location permission");
            hasLocationPermission = true;
            startLocationWatching();
        }
    }

    /**
     * Mark the position of device owner with blue circle on location updates.
     * @param location
     */
    private void markMyPosition(final Location location) {
        LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
        System.out.println(myLocation);
        final double r = 5.0;
        CircleOptions o = new CircleOptions().center(myLocation)
                .radius(r)
                .fillColor(Color.BLUE)
                .zIndex(2.0f)
                .strokeColor(Color.BLACK)
                .strokeWidth(2);
        Circle position = map.addCircle(o);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                position.remove();
            }
        }, 4000);
    }
    private void setUpMap() {
        map.getUiSettings().setIndoorLevelPickerEnabled(false);
        map.getUiSettings().setMapToolbarEnabled(false);
    }

    /**
     * Starts watching for location changes if possible under the current permissions.
     */
    @SuppressWarnings("MissingPermission")
    private void startLocationWatching() {
        if (!hasLocationPermission) {
            return;
        }
        if (map != null) {
            Log.v(TAG, "startLocationWatching enabled My Location");
            map.setMyLocationEnabled(true);
        }
        ContextCompat.startForegroundService(this, new Intent(this, LocationListener.class));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void stopLocationWatching() {
        stopService(new Intent(this, LocationListener.class));
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void ensureMapCentered(final Location location) {
        if (location != null && !centeredMap) {
            final float defaultMapZoom = 18f;
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), defaultMapZoom));
            centeredMap = true;
            if (map == null) {
                stopLocationWatching();
            }
        }
    }

    @Override
    protected void onDestroy() {
        // The super call is required for all activities
        super.onDestroy();
        // Stop the location service
        stopLocationWatching();
        // Unregister this activity's location listener
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
