package com.getprofessional.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.getprofessional.R;

public class Splash_Screen extends AppCompatActivity {

    private int SPLASH_TIME_OUT = 2000;
    private String session = "SESSION";
    private String username = "USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (checkSession()) {
                    Intent intent = new Intent(Splash_Screen.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Splash_Screen.this, LoginActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        }, SPLASH_TIME_OUT);

    }

    public Boolean checkSession() {
        String userNAME = null;
        SharedPreferences sharedPref = getSharedPreferences(session, MODE_PRIVATE);
        userNAME = sharedPref.getString(username, null);
        if (userNAME != null) {
            return true;
        } else {
            return false;
        }
    }

}
