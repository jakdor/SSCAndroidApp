package com.jakdor.sscapp.Timetable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakdor.sscapp.R;

public class PagerFragment extends Fragment {
    int pagerFragmentNum;

    //Create a new instance of CountingFragment, providing "num" as an argument.
    static PagerFragment newInstance(int num) {
        PagerFragment f = new PagerFragment();

        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    //When creating, retrieve this instance's number from its arguments.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagerFragmentNum = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager, container, false);

        //content loading here (?)

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //content loading here (?)
    }

}