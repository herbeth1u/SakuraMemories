package com.booboot.sakuramemories.activity;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.booboot.sakuramemories.R;
import com.booboot.sakuramemories.util.Weather;

public class SakuraFragment extends Fragment implements View.OnClickListener {
    public static final String WEATHER_START_INTENT = "WEATHER_START_INTENT";
    public static final String WEATHER_START_NO_PARTICLE_INTENT = "WEATHER_START_NO_PARTICLE_INTENT";
    public static final String WEATHER_STOP_INTENT = "WEATHER_STOP_INTENT";
    private Weather weather;
    private FloatingActionButton weatherButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sakura_fragment, container, false);

        weatherButton = (FloatingActionButton) rootView.findViewById(R.id.weatherButton);
        weatherButton.setOnClickListener(this);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(messageReceiver, new IntentFilter(WEATHER_START_INTENT));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(messageReceiver, new IntentFilter(WEATHER_START_NO_PARTICLE_INTENT));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(messageReceiver, new IntentFilter(WEATHER_STOP_INTENT));
        weather = new Weather(getActivity(), rootView.findViewById(R.id.cloud), rootView.findViewById(R.id.weatherOverlay));
        weatherButton.setImageResource(weather.getIcon());

        if (((MainActivity) getActivity()).isSakuraFragmentSelected()) {
            weather.startWeather();
        }

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weatherButton:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        weather.stopWeather();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(messageReceiver);
        super.onDestroyView();
    }

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case WEATHER_START_INTENT:
                    weather.startWeather();
                    break;

                case WEATHER_START_NO_PARTICLE_INTENT:
                    weather.startWeather(false);
                    break;

                case WEATHER_STOP_INTENT:
                    weather.stopWeather();
                    break;
            }
        }
    };
}
