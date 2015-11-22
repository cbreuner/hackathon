package edu.csumb.partyon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import edu.csumb.partyon.AppState;
import edu.csumb.partyon.R;

/**
 * Created by dante_000 on 11/21/2015.
 */

public class SplashScreenActivity extends AppCompatActivity {

    private AppState appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        FacebookSdk.sdkInitialize(this);

        appState = AppState.getInstance();
        appState.loginManager = LoginManager.getInstance();
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
