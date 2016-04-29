package com.appman.appmanager.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.appman.appmanager.fragments.FragmentExternalStorage;
import com.appman.appmanager.fragments.FragmentInternalStorage;

/**
 * Created by rudhraksh.pahade on 29-04-2016.
 */
public class SlidingTabAdapter extends FragmentStatePagerAdapter {

    CharSequence titles[];
    int numbOfTabs;

    public SlidingTabAdapter(FragmentManager fragmentManager, CharSequence mTitles[], int numbOfTabs){
        super(fragmentManager);
        this.titles = mTitles;
        this.numbOfTabs = numbOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new FragmentInternalStorage();
        }else if (position == 1){
            return new FragmentExternalStorage();
        }
        return null;
    }

    @Override
    public int getCount() {
        return numbOfTabs;
    }
}
