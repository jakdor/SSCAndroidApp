package com.jakdor.sscapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.jakdor.sscapp.Network.NetworkManager;

/**
 * Handles communication with NetworkManager and app lifecycle
 */
public abstract class BaseFragment extends Fragment {

    private final String CLASS_TAG = "BaseFragment";

    protected NetworkManager networkManager;

    protected Handler networkHandler = new Handler();
    protected Runnable networkStatusCheck = new Runnable() {
        @Override
        public void run() {
            if(networkManager.isDbReady() == 1){
                loadContent();
                networkHandler.removeCallbacks(this);
            }
            else if(networkManager.isDbReady() == 2){
                loadContent();
                networkManager.networkProblemInfoDisplayed();
                if(getContext() != null) {
                    Toast.makeText(getContext(), getString(R.string.net_no_server_connection),
                            Toast.LENGTH_SHORT).show();
                }
                networkHandler.removeCallbacks(this);
            }
            else if(networkManager.isDbReady() == 3){
                firstLoadFailure();
                networkHandler.removeCallbacks(this);
            }
            else {
                networkHandler.postDelayed(this, 10);
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkManager = NetworkManager.getInstance();
        networkHandler.postDelayed(networkStatusCheck, 50);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(MainActivity.appOnRestartCalled) {
            MainActivity.appOnRestartCalled = false;
            loadingUpdate();
            networkManager.checkForUpdate(getContext());
            networkHandler.postDelayed(networkStatusCheck, 10);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        networkHandler.removeCallbacks(networkStatusCheck);
    }

    protected void firstLoadFailure(){
        Log.e(CLASS_TAG, "Db no init data!, displaying warning...");
    }

    protected void loadingUpdate(){
        Log.i(CLASS_TAG, "Db check...");
    }

    protected void loadContent(){
        Log.i(CLASS_TAG, "Db ready, loading content...");
    }
}
