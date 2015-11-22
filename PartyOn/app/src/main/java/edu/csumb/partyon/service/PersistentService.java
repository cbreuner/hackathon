package edu.csumb.partyon.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import edu.csumb.partyon.AppState;
import edu.csumb.partyon.R;
import edu.csumb.partyon.activities.MainActivity;
import edu.csumb.partyon.constants.Constants;
import edu.csumb.partyon.db.Notification;

/**
 * Created by Tobias on 21.11.2015.
 * Should check notifications, invites, etc
 */
public class PersistentService extends Service {

    // Number of minutes between each ping
    private static final int INTERVAL = 2;
    @SuppressWarnings("FieldCanBeLocal")
    private static long INTERVAL_MS = INTERVAL * 1000 * 60;

    private Timer timer;
    private AppState appState;
    private NotificationManager nm;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("PartyOn", "Persistent service started.");
        appState = AppState.getInstance();

        if(nm == null)
            nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        buildInviteNotification("Chris");

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

    private void buildInviteNotification(String first_name){
        Intent acceptIntent = new Intent(this, MainActivity.class)
                , rejectIntent = new Intent(this, MainActivity.class);

        acceptIntent.putExtra(Constants.INVITE_INTENT, Constants.INVITE_INTENT_ACCEPT);
        rejectIntent.putExtra(Constants.INVITE_INTENT, Constants.INVITE_INTENT_REJECT);

        PendingIntent api = PendingIntent.getActivity(this, (int)System.currentTimeMillis(), acceptIntent, 0);
        PendingIntent rpi = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), rejectIntent, 0);

        NotificationCompat.Builder inviteBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher_big)
                        .setContentTitle("New invitation!")
                        .setContentText(first_name + " invited you to a party!")
                        .addAction(new NotificationCompat.Action(R.drawable.ic_done_black_24dp, "Accept", api))
                        .addAction(new NotificationCompat.Action(R.drawable.ic_clear_black_24dp, "Reject", rpi));

        android.app.Notification inviteNotification = inviteBuilder.build();

        nm.notify(0, inviteNotification);
    }

    @Override
    public void onDestroy() {
        Log.d("PartyOn", "Persistent service stopped.");
    }

    private class PingTask extends TimerTask {

        @Override
        public void run() {
            Log.d("PartyOn", "Checking for invites!");
        }
    }
}
