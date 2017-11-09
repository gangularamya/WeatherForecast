package com.demo.weatherforecast.ui;

import android.graphics.Bitmap;

import com.demo.weatherforecast.model.WeatherData;

/**
 * Created by ramya on 11/6/17.
 */

public interface MainFragmentView {

    void showProgress();
    void hideProgress();
    void checkSharedPrefs();
    void updateViews(WeatherData weatherData);
    void setImage(Bitmap responseObject);
}
