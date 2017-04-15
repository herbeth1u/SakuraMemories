package com.booboot.sakuramemories.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.booboot.sakuramemories.R;
import com.booboot.sakuramemories.adapter.BottomNavigationAdapter;

public class SakuraActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sakura_activity);

        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        bottomNavigation.setOnNavigationItemSelectedListener(this);
        BottomNavigationAdapter adapter = new BottomNavigationAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_sakura:
                viewPager.setCurrentItem(0);
                break;
            case R.id.navigation_memories:
                viewPager.setCurrentItem(1);
                break;
            case R.id.navigation_garden:
                viewPager.setCurrentItem(2);
                break;
        }
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.e("D", "onPageSelected");
        if (position != 0) {
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(SakuraFragment.WEATHER_STOP_INTENT));
        } else {
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(SakuraFragment.WEATHER_START_INTENT));
        }
        bottomNavigation.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
