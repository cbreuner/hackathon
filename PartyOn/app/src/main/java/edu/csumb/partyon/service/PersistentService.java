package edu.csumb.partyon.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tobias on 21.11.2015.
 * Should check notifications, invites, etc
 */
public class PersistentService extends Service {

    // Number of minutes between each ping
    private static final int INTERVAL = 2;
    @SuppressWarnings("FieldCanBeLocal")
    private static long INTERVAL_MS = INTERVAL * 1000 * 60;

    private Timer timer = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("PartyOn", "Persistent service started.");

        /* This didn't really work too well, didn't like being canceled
        if(timer != null)
            timer.cancel();
        else
            timer = new Timer();*/

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
        Log.d("PartyOn", "Persistent service stopped.");
    }

    private class PingTask extends TimerTask {

        @Override
        public void run() {
            Log.d("PartyOn", "Checking for notifications and invites!");
        }
    }
}
