package edu.csumb.partyon.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import edu.csumb.partyon.R;
import edu.csumb.partyon.constants.Constants;
import io.realm.internal.Context;

/**
 * Created by dante_000 on 11/21/2015.
 */

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        if(getPreferences().contains(Constants.USER_ID))
        {
            Intent send_to_main = new Intent(this, MainActivity.class);
            startActivity(send_to_main);
            finish();
        }
        else{
            Intent send_to_login = new Intent(this, fbLogin.class);
            startActivity(send_to_login);
            finish();
        }

    }

    public SharedPreferences getPreferences(){
        return getSharedPreferences(Constants.USER_INFO, MODE_PRIVATE);
    }

    }
