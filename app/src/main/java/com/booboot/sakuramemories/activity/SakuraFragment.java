package com.booboot.sakuramemories.activity;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.booboot.sakuramemories.R;
import com.booboot.sakuramemories.util.App;
import com.booboot.sakuramemories.util.Weather;

public class SakuraFragment extends Fragment {
    public static final String WEATHER_START_INTENT = "WEATHER_START_INTENT";
    public static final String WEATHER_STOP_INTENT = "WEATHER_STOP_INTENT";
    private Weather weather;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sakura_fragment, container, false);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(messageReceiver, new IntentFilter(WEATHER_START_INTENT));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(messageReceiver, new IntentFilter(WEATHER_STOP_INTENT));
        weather = new Weather(getActivity(), this, getActivity().findViewById(R.id.cloud), rootView.findViewById(R.id.weatherOverlay));

        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case App.ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    weather.getWeather();
                }
                break;
            }
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

                case WEATHER_STOP_INTENT:
                    weather.stopWeather();
                    break;
            }
        }
    };
}
