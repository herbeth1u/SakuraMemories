package com.booboot.sakuramemories.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.booboot.sakuramemories.R;
import com.booboot.sakuramemories.utils.Weather;

import xyz.matteobattilana.library.WeatherView;

public class SakuraActivity extends AppCompatActivity {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sakura_activity);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        WeatherView weatherView = (WeatherView) findViewById(R.id.weatherView);
        View weatherOverlay = findViewById(R.id.weatherOverlay);
        Weather.getRandom(this, weatherView, weatherOverlay);
    }
}
