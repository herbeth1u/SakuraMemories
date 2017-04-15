package com.booboot.sakuramemories.activity;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

import com.booboot.sakuramemories.R;
import com.plattysoft.leonids.ParticleSystem;

public class SakuraFragment extends Fragment {
    public static final String WEATHER_START_INTENT = "WEATHER_START_INTENT";
    public static final String WEATHER_STOP_INTENT = "WEATHER_STOP_INTENT";
    private View cloud;
    private ParticleSystem particleSystem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sakura_fragment, container, false);

        cloud = getActivity().findViewById(R.id.cloud);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(messageReceiver, new IntentFilter(WEATHER_START_INTENT));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(messageReceiver, new IntentFilter(WEATHER_STOP_INTENT));
        startWeather();

        return rootView;
    }

    private void startWeather() {
        particleSystem = new ParticleSystem(getActivity(), 30 * 8000 / 1000, R.drawable.rain, 8000)
                .setAcceleration(0.00013f, 90 - (-6))
                .setInitialRotationRange(-6, 6)
                .setSpeedByComponentsRange(0f, 0f, 0.05f, 0.1f)
                //.setSpeedRange(0.2f, 0.5f)
                .setFadeOut(7000, new AccelerateInterpolator());

        cloud.post(new Runnable() {
            @Override
            public void run() {
                particleSystem.emitWithGravity(cloud, Gravity.BOTTOM, 30);
            }
        });
    }

    private void stopWeather() {
        if (particleSystem != null) particleSystem.cancel();
    }

    @Override
    public void onDestroyView() {
        stopWeather();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(messageReceiver);
        super.onDestroyView();
    }

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case WEATHER_START_INTENT:
                    startWeather();
                    break;

                case WEATHER_STOP_INTENT:
                    stopWeather();
                    break;
            }
        }
    };
}
