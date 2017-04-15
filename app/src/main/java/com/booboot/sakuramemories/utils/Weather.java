package com.booboot.sakuramemories.utils;

import android.content.Context;
import android.view.View;

import com.booboot.sakuramemories.R;

import java.util.concurrent.ThreadLocalRandom;

import xyz.matteobattilana.library.Common.Constants;
import xyz.matteobattilana.library.WeatherView;

/**
 * Created by od on 15/04/2017.
 */
public class Weather {
    private final static int FPS = 60;
    private final static Constants.orientationStatus orientationMode = Constants.orientationStatus.ENABLE;

    public static void getSun(Context context, WeatherView weatherView, View weatherOverlay) {
        weatherOverlay.setBackgroundColor(context.getResources().getColor(R.color.sunOverlay));
        weatherView.setWeather(Constants.weatherStatus.SUN)
                .setFPS(FPS)
                .setOrientationMode(orientationMode)
                .startAnimation();
    }

    public static void getRain(Context context, WeatherView weatherView, View weatherOverlay) {
        weatherOverlay.setBackgroundColor(context.getResources().getColor(R.color.rainOverlay));
        weatherView.setWeather(Constants.weatherStatus.RAIN)
                .setFPS(FPS)
                .setOrientationMode(orientationMode)
                .setCurrentAngle(-3)
                .setCurrentFadeOutTime(4000)
                .setCurrentLifeTime(8000)
                .setCurrentParticles(30)
                .startAnimation();
    }

    public static void getSnow(Context context, WeatherView weatherView, View weatherOverlay) {
        weatherOverlay.setBackgroundColor(context.getResources().getColor(R.color.snowOverlay));
        weatherView.setWeather(Constants.weatherStatus.SNOW)
                .setFPS(FPS)
                .setOrientationMode(orientationMode)
                .setCurrentAngle(-3)
                .setCurrentFadeOutTime(7000)
                .setCurrentLifeTime(8000)
                .setCurrentParticles(30)
                .startAnimation();
    }

    public static void getRandom(Context context, WeatherView weatherView, View weatherOverlay) {
        int randomNum = ThreadLocalRandom.current().nextInt(0, 2 + 1);
        switch (randomNum) {
            case 0:
                getSun(context, weatherView, weatherOverlay);
                return;

            case 1:
                getRain(context, weatherView, weatherOverlay);
                return;

            case 2:
                getSnow(context, weatherView, weatherOverlay);
                return;
        }
    }
}
