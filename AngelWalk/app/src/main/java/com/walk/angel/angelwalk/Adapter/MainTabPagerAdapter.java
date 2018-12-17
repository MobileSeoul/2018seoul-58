package com.walk.angel.angelwalk.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.walk.angel.angelwalk.Fragment.HomeFragment;
import com.walk.angel.angelwalk.Fragment.MapFragment;
import com.walk.angel.angelwalk.Fragment.SettingFragment;

public class MainTabPagerAdapter extends FragmentStatePagerAdapter {

    // Count number of tabs
    private int tabCount;

    public MainTabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                MapFragment map = new MapFragment();
                return map;
            case 1:
                HomeFragment home = new HomeFragment();
                return home;
            case 2:
                SettingFragment setting = new SettingFragment();
                return setting;
                //BoardFragment board = new BoardFragment();
                //return board;
            //case 3:
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}