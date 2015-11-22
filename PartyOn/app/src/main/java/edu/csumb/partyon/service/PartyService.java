package edu.csumb.partyon.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Timer;
import java.util.TimerTask;

import edu.csumb.partyon.AppState;

/**
 * Created by Tobias on 21.11.2015.
 * Should only remain running while party is active
 * Is used for reporting GPS location and compass direction
 */
public class PartyService extends Service implements SensorEventListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    // Number of seconds between each ping
    private static final int INTERVAL = 60;
    @SuppressWarnings("FieldCanBeLocal")
    private static long INTERVAL_MS = INTERVAL * 1000;

    private Timer timer;
    private SensorManager sm;
    private AppState appState;
    private LocationRequest request;
    private GoogleApiClient googleApiClient;
    private Sensor accelerometer, magnometer;

    private float[] mGravity;
    private float[] mGeomagnetic;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("PartyOn", "Party service started.");

        appState = AppState.getInstance();

        if(sm == null)
            sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if(accelerometer == null)
            accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(magnometer == null)
            magnometer = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sm.registerListener(this, magnometer, SensorManager.SENSOR_DELAY_UI);

        request = new LocationRequest();
        request.setInterval(INTERVAL_MS)
                .setFastestInterval(0)
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();

        if(timer == null)
            timer = new Timer();

        timer.scheduleAtFixedRate(new PingTask(), 0, INTERVAL_MS);

        return START_STICKY;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        Log.d("PartyOn", "Party service stopped.");
        sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;
        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                appState.lastAzimut = orientation[0];
                //Log.d("PartyOn", "Azimut logged: " + orientation[0]);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, request, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        appState.lastLocation = location;
        Log.d("PartyOn GPS", "New location! " + location.toString());
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private class PingTask extends TimerTask {

        @Override
        public void run() {
            Log.d("PartyOn", "Checking for notifications and party changes, and submitting GPS!");
            refreshData();
        }
    }

    private void refreshData(){

    }
}
