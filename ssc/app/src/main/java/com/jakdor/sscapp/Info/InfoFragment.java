package com.jakdor.sscapp.Info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakdor.sscapp.BaseFragment;
import com.jakdor.sscapp.R;

import butterknife.ButterKnife;

public class InfoFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
