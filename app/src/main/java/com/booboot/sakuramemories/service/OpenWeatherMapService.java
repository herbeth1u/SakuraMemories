package com.booboot.sakuramemories.service;

import com.booboot.sakuramemories.model.OWMResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by od on 15/04/2017.
 */
public interface OpenWeatherMapService {
    String base = "data/2.5";
    String path = "/weather";

    @GET(base + path)
    Call<OWMResult> getWeather(@Query("APPID") String appID, @Query("lat") double lat, @Query("lon") double lon);
}

