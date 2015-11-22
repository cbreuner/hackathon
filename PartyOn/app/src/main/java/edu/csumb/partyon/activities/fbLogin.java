package edu.csumb.partyon.activities;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;

import edu.csumb.partyon.R;

/**
 * Created by dante_000 on 11/21/2015.
 */
public class fbLogin extends AppCompatActivity {
    private TextView info;
    private TextView userName;
    private LoginButton loginbutton;
    private CallbackManager callBackManager;
    private LoginResult loginresult;
    private void sendToMain(){
        Intent send_to_main2 = new Intent(this, MainActivity.class);
        startActivity(send_to_main2);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.fragment_fbloginfragment);
        callBackManager = CallbackManager.Factory.create();
        info = (TextView)findViewById(R.id.info);
        loginbutton = (LoginButton)findViewById(R.id.login_button);
        loginbutton.registerCallback(callBackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("PartyOn", "I got into the onSuccess!");
                info.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()

                );
                SharedPreferences user_info_preferences = getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
                SharedPreferences.Editor user_info_editor = user_info_preferences.edit();
                user_info_editor.putString("USER_INFO_ID", loginResult.getAccessToken().getUserId());
                user_info_editor.putString("USER_INFO_ACC_TOKEN", loginResult.getAccessToken().getToken());
                user_info_editor.commit();
                sendToMain();
            }

            @Override
            public void onCancel() {
                info.setText("Login attempt canceled.");

            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed.");

            }

            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                callBackManager.onActivityResult(requestCode, resultCode, data);
            }
        });
    }


}