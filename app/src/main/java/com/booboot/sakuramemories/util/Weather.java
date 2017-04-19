package com.booboot.sakuramemories.util;

import android.app.Activity;
import android.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.booboot.sakuramemories.R;
import com.plattysoft.leonids.ParticleSystem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by od on 15/04/2017.
 */
public class Weather {
    public static final int SUN = 1;
    public static final int RAIN = 2;
    public static final int SNOW = 3;
    public static final int MIST = 4;
    public static final int CLOUDS = 5;

    public static final int LIFETIME = 8000;
    public static final int FADE_OUT = 7000;
    public static final int RAIN_PARTICLES = 30;
    public static final int SNOW_PARTICLES = 10;
    public static final int RAIN_ANGLE = -6;
    public static final int SNOW_ANGLE = 0;

    private Activity activity;
    private Fragment fragment;
    private View anchor, weatherOverlay;
    private ParticleSystem particleSystem;
    private int weather;

    public Weather(Activity activity, Fragment fragment, View anchor, View weatherOverlay) {
        this.activity = activity;
        this.fragment = fragment;
        this.anchor = anchor;
        this.weatherOverlay = weatherOverlay;
        this.weather = getWeather();
        startWeather();
    }

    private static int getWeather() {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        int hash;
        try {
            Date todayWithZeroTime = formatter.parse(formatter.format(new Date()));
            hash = todayWithZeroTime.hashCode();
        } catch (ParseException e) {
            return SUN;
        }

        if (hash % 5 == 0) return SNOW;
        else if (hash % 3 == 0) return RAIN;
        else return SUN;
    }

    public ParticleSystem startWeather() {
        stopWeather();

        switch (weather) {
            case RAIN:
                weatherOverlay.setBackgroundColor(activity.getResources().getColor(R.color.rainOverlay));
                particleSystem = new ParticleSystem(activity, RAIN_PARTICLES * LIFETIME / 1000, R.drawable.rain, LIFETIME)
                        .setAcceleration(0.00013f, 90 - RAIN_ANGLE)
                        .setInitialRotationRange(RAIN_ANGLE, -RAIN_ANGLE)
                        .setSpeedByComponentsRange(0f, 0f, 0.05f, 0.1f)
                        .setFadeOut(FADE_OUT, new AccelerateInterpolator());

                anchor.post(new Runnable() {
                    @Override
                    public void run() {
                        particleSystem.emitWithGravity(anchor, Gravity.BOTTOM, RAIN_PARTICLES);
                    }
                });
                break;

            case SNOW:
                weatherOverlay.setBackgroundColor(activity.getResources().getColor(R.color.snowOverlay));
                particleSystem = new ParticleSystem(activity, SNOW_PARTICLES * LIFETIME / 1000, R.drawable.snow, LIFETIME)
                        .setSpeedByComponentsRange(0f, 0f, 0.05f, 0.1f)
                        .setFadeOut(FADE_OUT, new AccelerateInterpolator());

                anchor.post(new Runnable() {
                    @Override
                    public void run() {
                        particleSystem.emitWithGravity(anchor, Gravity.BOTTOM, SNOW_PARTICLES);
                    }
                });
                break;

            case MIST:
                weatherOverlay.setBackgroundColor(activity.getResources().getColor(R.color.snowOverlay));
                break;

            case CLOUDS:
                weatherOverlay.setBackgroundColor(activity.getResources().getColor(R.color.rainOverlay));
                break;

            default:
                weatherOverlay.setBackgroundColor(activity.getResources().getColor(R.color.sunOverlay));
        }

        return particleSystem;
    }

    public void stopWeather() {
        if (particleSystem != null) particleSystem.cancel();
    }
}
