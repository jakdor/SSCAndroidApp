package com.jakdor.sscapp.Timetable;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakdor.sscapp.Network.NetworkManager;
import com.jakdor.sscapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimetableFragment extends Fragment {

    @BindView(R.id.testText)
    TextView testView;

    private final String CLASS_TAG = "TimetableFragment";

    private NetworkManager networkManager;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(networkManager.isDbReady()){
                testView.setText("got db!");
            }
            else {
                handler.postDelayed(this, 10);
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timetable_fragment, container, false);
        ButterKnife.bind(this, view);

        networkManager = NetworkManager.getInstance();
        handler.postDelayed(runnable, 10);

        return view;
    }

    @OnClick(R.id.testButton)
    public void onTestClick(View view){
        testView.setText("HI!!!");
    }

}
