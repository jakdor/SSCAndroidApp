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

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            int data = extras.getInt("notifDisp");
            if(data == 1){
                intent.putExtra("notifDisp", 1);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            }
        }

        startActivity(intent);
        finish();
    }

}
