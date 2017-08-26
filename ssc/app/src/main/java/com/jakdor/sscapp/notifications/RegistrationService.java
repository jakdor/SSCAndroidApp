package com.jakdor.sscapp.notifications;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.jakdor.sscapp.R;


public class RegistrationService extends IntentService {

    private final String CLASS_TAG = "RegistrationService";

    public RegistrationService() {
        super("RegistrationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID myID = InstanceID.getInstance(this);
        String registrationToken = null;

        try {
            registrationToken = myID.getToken(
                    getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE,
                    null
            );
            Log.i(CLASS_TAG, "Got registration token: " + registrationToken);
        }
        catch (Exception e){
            Log.e(CLASS_TAG, "Can't get registration token");
        }

        if(registrationToken != null){
            GcmPubSub subscription = GcmPubSub.getInstance(this);
            try {
                subscription.subscribe(registrationToken, "/topics/sscapp", null);
            }
            catch (Exception e){
                Log.e(CLASS_TAG, "can't subscribe to notification topic");
            }
        }
    }
}
