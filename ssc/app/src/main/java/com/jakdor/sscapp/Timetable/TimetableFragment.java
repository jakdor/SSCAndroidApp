package com.jakdor.sscapp.Timetable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakdor.sscapp.BaseFragment;
import com.jakdor.sscapp.Model.Timetable;
import com.jakdor.sscapp.Network.NetworkManager;
import com.jakdor.sscapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimetableFragment extends BaseFragment {

    @BindView(R.id.testText)
    TextView testView;

    private final String CLASS_TAG = "TimetableFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timetable_fragment, container, false);
        ButterKnife.bind(this, view);

        networkManager = NetworkManager.getInstance();
        networkHandler.postDelayed(networkStatusCheck, 10);

        return view;
    }

    @Override
    protected void loadContent(){
        super.loadContent();
        testView.setText(Timetable.findById(Timetable.class, (long)1).getName());
    }

    @Override
    protected void loadingUpdate() {
        super.loadingUpdate();
        testView.setText("Loading update...");
    }

    @Override
    protected void firstLoadFailure() {
        super.firstLoadFailure();
        testView.setText(getString(R.string.net_no_init_data));
    }

    @OnClick(R.id.testButton)
    public void onTestClick(View view){
        testView.setText("HI!!!");
    }

}
