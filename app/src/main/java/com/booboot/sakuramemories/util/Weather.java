package com.booboot.sakuramemories.util;

import android.app.Activity;
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
    public static final int RAIN_ANGLE = 10;
    public static final int SNOW_ANGLE = 0;

    private Activity activity;
    private View anchor, weatherOverlay;
    private ParticleSystem particleSystem;
    private int weather;

    public Weather(Activity activity, View anchor, View weatherOverlay) {
        this.activity = activity;
        this.anchor = anchor;
        this.weatherOverlay = weatherOverlay;
        this.weather = getWeather();
    }

    private static int getWeather() {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        int hash;
        try {
            Date todayWithZeroTime = formatter.parse(formatter.format(new Date()));
            hash = todayWithZeroTime.hashCode() % 10;
        } catch (ParseException e) {
            return SUN;
        }

        if (hash >= 0 && hash <= 4) return SUN; // 50%
        else if (hash >= 5 && hash <= 7) return RAIN; // 30%
        else return SNOW; // 20%
    }

    public ParticleSystem startWeather(boolean particles) {
        // stopWeather();
        if (particleSystem != null) return particleSystem;

        switch (weather) {
            case RAIN:
                weatherOverlay.setBackgroundColor(activity.getResources().getColor(R.color.rainOverlay));
                if (particles) {
                    particleSystem = new ParticleSystem(activity, RAIN_PARTICLES * LIFETIME / 1000, R.drawable.rain_particle, LIFETIME)
                            .setAcceleration(0.00013f, 90 - RAIN_ANGLE)
                            .setInitialRotationRange(RAIN_ANGLE, RAIN_ANGLE)
                            .setSpeedByComponentsRange(-0.05f, -0.1f, 0.15f, 0.3f)
                            .setFadeOut(FADE_OUT, new AccelerateInterpolator());

                    anchor.post(new Runnable() {
                        @Override
                        public void run() {
                            if (particleSystem == null) return;
                            particleSystem.emitWithGravity(anchor, Gravity.BOTTOM, RAIN_PARTICLES);
                        }
                    });
                }
                break;

            case SNOW:
                weatherOverlay.setBackgroundColor(activity.getResources().getColor(R.color.snowOverlay));
                if (particles) {
                    particleSystem = new ParticleSystem(activity, SNOW_PARTICLES * LIFETIME / 1000, R.drawable.snow_particle, LIFETIME)
                            .setSpeedByComponentsRange(0f, 0f, 0.05f, 0.1f)
                            .setFadeOut(FADE_OUT, new AccelerateInterpolator());

                    anchor.post(new Runnable() {
                        @Override
                        public void run() {
                            if (particleSystem == null) return;
                            particleSystem.emitWithGravity(anchor, Gravity.BOTTOM, SNOW_PARTICLES);
                        }
                    });
                }
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

    public ParticleSystem startWeather() {
        return startWeather(true);
    }

    public void stopWeather() {
        if (particleSystem != null) {
            particleSystem.cancel();
            particleSystem = null;
        }
        weatherOverlay.setBackgroundColor(0);
    }
}
