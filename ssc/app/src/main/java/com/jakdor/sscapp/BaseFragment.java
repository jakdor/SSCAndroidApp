package com.jakdor.sscapp;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.jakdor.sscapp.Network.NetworkManager;

public class BaseFragment extends Fragment {

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
                Toast.makeText(getContext(), getString(R.string.net_no_server_connection),
                        Toast.LENGTH_SHORT).show();
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
    public void onResume() {
        super.onResume();
        if(MainActivity.appOnRestartCalled) {
            MainActivity.appOnRestartCalled = false;
            loadingUpdate();
            networkManager.checkForUpdate(getContext());
            networkHandler.postDelayed(networkStatusCheck, 10);
        }
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
