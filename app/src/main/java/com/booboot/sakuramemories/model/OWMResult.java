package com.booboot.sakuramemories.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

/**
 * Created by od on 15/04/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OWMResult {
    private Map<String, Float> coord;
    private List<OWMWeather> weather;

    public Map<String, Float> getCoord() {
        return coord;
    }

    public void setCoord(Map<String, Float> coord) {
        this.coord = coord;
    }

    public List<OWMWeather> getWeather() {
        return weather;
    }

    public void setWeather(List<OWMWeather> weather) {
        this.weather = weather;
    }
}
