package com.booboot.sakuramemories.util;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.booboot.sakuramemories.BuildConfig;
import com.booboot.sakuramemories.R;
import com.booboot.sakuramemories.factory.RetrofitFactory;
import com.booboot.sakuramemories.model.OWMResult;
import com.booboot.sakuramemories.service.OpenWeatherMapService;
import com.plattysoft.leonids.ParticleSystem;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

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
        getWeather();
    }

    public void getWeather() {
        LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            FragmentCompat.requestPermissions(fragment, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, App.ACCESS_FINE_LOCATION);
            weather = SUN;
            startWeather();
        } else {
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                weather = SUN;
                startWeather();
            } else {
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();

                final Call<OWMResult> call = RetrofitFactory.get(OpenWeatherMapService.class).getWeather(BuildConfig.OPEN_WEATHER_API_KEY, latitude, longitude);
                RetrofitFactory.async(activity, call, new RetrofitFactory.Callback<OWMResult>() {
                    @Override
                    public void success(Response<OWMResult> response) {
                        OWMResult result = response.body();
                        if (result.getWeather() != null && result.getWeather().size() > 0) {
                            int id = result.getWeather().get(0).getId();
                            if (id < 600) {
                                weather = RAIN;
                            } else if (id < 700) {
                                weather = SNOW;
                            } else if (id < 800) {
                                weather = MIST;
                            } else if (id < 803) {
                                weather = SUN;
                            } else if (id < 900) {
                                weather = CLOUDS;
                            } else if (id < 957) {
                                weather = SUN;
                            } else {
                                weather = MIST;
                            }
                            startWeather();
                        }
                    }
                });
            }
        }
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
