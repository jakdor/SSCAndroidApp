package com.jakdor.sscapp.Timetable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static com.jakdor.sscapp.Timetable.TimetableFragment.NUM_ITEMS;

class PagerAdapter extends FragmentPagerAdapter {

    private final String[] tabName = {"Sharing", "Learning", "Networking"};

    PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        return PagerFragment.newInstance(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabName[position];
    }
}