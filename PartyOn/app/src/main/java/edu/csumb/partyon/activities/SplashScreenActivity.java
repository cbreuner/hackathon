package edu.csumb.partyon.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import edu.csumb.partyon.AppState;
import edu.csumb.partyon.R;

/**
 * Created by dante_000 on 11/21/2015.
 */

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        FacebookSdk.sdkInitialize(this);

        AppState appState = AppState.getInstance();
        appState.loginManager = LoginManager.getInstance();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        }
        redirect(AccessToken.getCurrentAccessToken() != null);
    }

    private void redirect(boolean userLoggedIn){
        Intent intent;
        if(userLoggedIn)
            intent = new Intent(this, MainActivity.class);
        else
            intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
