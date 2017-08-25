package com.jakdor.sscapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jakdor.sscapp.Network.NetworkManager;
import com.jakdor.sscapp.notifications.RegistrationService;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent registrationIntent = new Intent(this, RegistrationService.class);
        startService(registrationIntent);

        NetworkManager networkManager = NetworkManager.getInstance();
        networkManager.checkForUpdate(getApplicationContext());

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
