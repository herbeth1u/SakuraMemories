package com.booboot.sakuramemories.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.booboot.sakuramemories.activity.MemoriesFragment;
import com.booboot.sakuramemories.activity.SakuraFragment;

/**
 * Created by od on 15/04/2017.
 */
public class BottomNavigationAdapter extends FragmentStatePagerAdapter {
    private final static int NUM_TABS = 3;
    private int type;

    public BottomNavigationAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        Fragment tab;

        switch (position) {
            case 0:
                tab = new SakuraFragment();
                break;
            case 1:
                tab = new MemoriesFragment();
                break;
            default:
                tab = new MemoriesFragment();
                break;
        }

        return tab;
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }

}
