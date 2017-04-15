package com.booboot.sakuramemories.factory;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by od on 15/04/2017.
 */

public class RetrofitFactory {
    private final static String OPEN_WEATHER_MAP_API = "http://api.openweathermap.org/";
    public static Retrofit instance;
    private static Toast toast;
    private static CountDownTimer toastTimer;

    public static <T> T get(Class<T> service) {
        if (instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl(OPEN_WEATHER_MAP_API)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
        }

        return instance.create(service);
    }

    public static <T> void async(final Context context, Call<T> call, final Callback<T> callback) {
        call.enqueue(new retrofit2.Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (context == null) return;

                if (response.isSuccessful()) {
                    callback.success(response);
                } else {
                    callback.fail(context, response, null);
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (context == null) return;
                callback.fail(context, null, t);
            }
        });
    }

    public static abstract class Callback<T> {
        public abstract void success(Response<T> response);

        public void fail(Context context, Response<T> response, Throwable exception) {
            if (response != null) showToast(context, response.message());
            else if (exception != null) showToast(context, exception.getMessage());
        }
    }

    public static void showToast(final Context context, final String message) {
        if (message == null || context == null) return;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (toast != null) toast.cancel();
                if (toastTimer != null) toastTimer.cancel();

                toast = Toast.makeText(context, message, message.length() > 40 ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
                toast.show();
                if (message.length() > 90) {
                    toastTimer = new CountDownTimer(8000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            toast.show();
                        }

                        public void onFinish() {
                            toast.cancel();
                        }
                    }.start();
                }
            }
        });
    }
}
