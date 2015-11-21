package edu.csumb.partyon.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Tobias on 21.11.2015.
 * Should only remain running while party is active
 * Is used for reporting GPS location
 */
public class PartyService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("PartyOn", "Party service started.");
        return START_STICKY;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        Log.d("PartyOn", "Party service stopped.");
    }
}
