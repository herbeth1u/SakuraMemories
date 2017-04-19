package com.booboot.sakuramemories.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.booboot.sakuramemories.R;
import com.booboot.sakuramemories.adapter.BottomNavigationAdapter;
import com.booboot.sakuramemories.util.Weather;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigation;
    private Weather weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        bottomNavigation.setOnNavigationItemSelectedListener(this);
        BottomNavigationAdapter adapter = new BottomNavigationAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        weather = new Weather(this, findViewById(R.id.cloud), findViewById(R.id.weatherOverlay));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (viewPager.getCurrentItem() == 0) {
            weather.startWeather();
        }
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
        if (position != 0) {
            weather.stopWeather();
        } else {
            weather.startWeather();
        }
        bottomNavigation.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    protected void onDestroy() {
        weather.stopWeather();
        super.onDestroy();
    }
}
