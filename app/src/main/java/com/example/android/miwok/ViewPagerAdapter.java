package com.example.android.miwok;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Ron on 1/14/2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new NumbersFragment();
            case 1: return new FamilyFragment();
            case 2: return new ColorsFragment();
            case 3: return new PhrasesFragment();
            default: return null;
        }
    }
    @Override
    public int getCount() {
        return 4;
    }
}
