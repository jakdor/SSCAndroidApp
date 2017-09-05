package com.jakdor.sscapp.Timetable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jakdor.sscapp.NetContentBaseFragment;
import com.jakdor.sscapp.R;

import java.util.Calendar;

import butterknife.BindView;

public class TimetableFragment extends NetContentBaseFragment {

    @BindView(R.id.contentLayout)
    LinearLayout contentLayout;
    @BindView(R.id.timetable_pager)
    ViewPager viewPager;

    private View fragmentView;
    private static int savedPageNum = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.timetable_fragment, container, false);
        createView(fragmentView);

        contentLayout.setVisibility(View.GONE);

        return fragmentView;
    }

    @Override
    protected void loadContent(){
        super.loadContent();
        setupPager();
        viewPager.setCurrentItem(savedPageNum);
        contentLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void removeContentWhileLoad() {
        contentLayout.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        savedPageNum = viewPager.getCurrentItem();
    }

    static final int NUM_ITEMS = 4;

    private void setupPager(){
        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        viewPager.post(() -> {
            viewPager.setCurrentItem(getDay());
        });

        SlidingTabLayout slidingTabLayout = fragmentView.findViewById(R.id.sliding_tabs);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(
                ContextCompat.getColor(getContext(), R.color.colorPrimary));
        slidingTabLayout.setViewPager(viewPager);
    }

    private int getDay(){
        Calendar calendar = Calendar.getInstance();
        int calMonth = calendar.get(Calendar.MONTH);
        if(calMonth != 8) {
            return 0;
        }

        int calDay = calendar.get(Calendar.DAY_OF_MONTH);

        if(calDay >= 21 && calDay <= 24){
            return calDay - 21;
        }
        else {
            return 0;
        }
    }
}
