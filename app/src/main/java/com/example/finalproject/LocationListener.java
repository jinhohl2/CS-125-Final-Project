package com.example.finalproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

public final class LocationListener extends Service {

    private static final String TAG = "LocationListener";

    private FusedLocationProviderClient locationClient;

    private LocationCallback locationCallback;

    private LocalBroadcastManager broadcastSender;

    private static final String NOTIFICATION_CHANNEL = "CS125Location";

    private static final int NOTIFICATION_ID = 125;

    private static final int LOCATION_UPDATE_INTERVAL = 5000;

    private static final LocationRequest LOCATION_REQUEST = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(LOCATION_UPDATE_INTERVAL);

    static final String UPDATE_ACTION = "LocationUpdate";

    static final String UPDATE_DATA_ID = "Location";

    @Override
    public void onCreate() {
        broadcastSender = LocalBroadcastManager.getInstance(this);
        locationClient = new FusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(final LocationResult locationResult) {
                Log.v(TAG, "onLocationResult");
                if (locationResult != null && locationResult.getLastLocation() != null) {
                    Log.v(TAG, "Usable location");
                    gotLocationUpdate(locationResult.getLastLocation());
                }
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    NOTIFICATION_CHANNEL,
                    getResources().getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_LOW);
            channel.setSound(null, null);
            channel.setShowBadge(false);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Log.v(TAG, "Created notification channel");
        }
    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        Log.v(TAG, "Starting");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
                .setOngoing(true)
                .setPriority(NotificationManager.IMPORTANCE_DEFAULT);
        startForeground(NOTIFICATION_ID, builder.build());
        try {
            locationClient.requestLocationUpdates(LOCATION_REQUEST, locationCallback, Looper.myLooper());
            Log.v(TAG, "Requested location updates");
        } catch (SecurityException e) {
            // Shouldn't happen - the service is only started after permission is granted
            Log.e(TAG, "requestLocationUpdates failed", e);
        }
        return START_STICKY;
    }

    private void gotLocationUpdate(final Location location) {
        Intent update = new Intent();
        update.setAction(UPDATE_ACTION);
        update.putExtra(UPDATE_DATA_ID, location);
        broadcastSender.sendBroadcast(update);
    }

    @Override
    public void onDestroy() {
        if (locationClient != null) {
            locationClient.removeLocationUpdates(locationCallback);
            locationClient = null;
            Log.v(TAG, "Removed location updates");
        }
        Log.v(TAG, "Destroyed");
    }

    @Nullable
    public IBinder onBind(final Intent intent) {
        return null;
    }



}
