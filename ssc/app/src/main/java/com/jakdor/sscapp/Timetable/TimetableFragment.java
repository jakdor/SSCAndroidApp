package com.jakdor.sscapp.Timetable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakdor.sscapp.NetContentBaseFragment;
import com.jakdor.sscapp.R;

import butterknife.BindView;


public class TimetableFragment extends NetContentBaseFragment {

    private final String CLASS_TAG = "TimetableFragment";

    @BindView(R.id.test)
    TextView test;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timetable_fragment, container, false);
        createView(view);

        return view;
    }

    @Override
    protected void loadContent(){
        super.loadContent();
        test.setVisibility(View.VISIBLE);
        test.setText("it works! duh");
    }

    @Override
    protected void removeContentWhileLoad() {
        test.setVisibility(View.GONE);
    }
}
