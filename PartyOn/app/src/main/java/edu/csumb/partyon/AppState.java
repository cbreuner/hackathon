package edu.csumb.partyon;

import android.location.Location;

import com.facebook.login.LoginManager;

/**
 * Created by Tobias on 20.11.2015.
 * Singleton state-keeping for the app
 */
public class AppState {

    public boolean partyActive = false;
    public Location lastLocation;
    public float lastAzimut = -1;
    public LoginManager loginManager;
    public String sInvites;
    public String[] aInvites;

    private static AppState instance;

    private AppState(){}

    public static AppState getInstance(){
        if(instance == null)
            instance = new AppState();
        return instance;
    }
}
