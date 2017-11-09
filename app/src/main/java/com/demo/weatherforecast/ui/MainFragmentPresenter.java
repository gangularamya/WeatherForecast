package com.demo.weatherforecast.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.android.volley.Request;
import com.demo.weatherforecast.data.webservices.NetworkRequestHelper;
import com.demo.weatherforecast.model.WeatherData;
import com.demo.weatherforecast.util.Constants;
import com.google.gson.Gson;

/**
 * Created by ramya on 11/6/17.
 */

public class MainFragmentPresenter{
    private MainFragmentView mView;
    private Context context;
    private NetworkRequestHelper networkRequestHelper;

    /* The request completed listener */
    private NetworkRequestHelper.OnRequestCompletedListener requestCompletedListener = new NetworkRequestHelper.OnRequestCompletedListener() {
        @Override
        public void onRequestCompleted(String requestName, boolean status, String response, String errorMessage) {
            mView.hideProgress();
            if (status) {
                WeatherData weatherData = new Gson().fromJson(response, WeatherData.class);
                mView.updateViews(weatherData);
            }

        }
    };

    /* The Image request completed listener */
    private NetworkRequestHelper.OnImageRequestListener imageRequestCompletedListener = new NetworkRequestHelper.OnImageRequestListener() {
        @Override
        public void onImageRequestCompleted(String requestName, boolean status, Bitmap response, String errorMessage) {
            mView.hideProgress();
            if (status) {
                mView.setImage(response);
            }

        }
    };

    public MainFragmentPresenter(Context context, MainFragmentView view) {
        this.context = context;
        this.mView = view;
        networkRequestHelper = new NetworkRequestHelper(context, requestCompletedListener, imageRequestCompletedListener);
    }

    //API Success
    public void fetchData(String city) {
        Uri uri  = Uri.parse(Constants.OPEN_WEATHER_API).buildUpon()
                .appendQueryParameter(Constants.WEATHER_FORECAST_PARAM, city)
                .appendQueryParameter(Constants.UNITS_PARAM, Constants.METRICS_PARAM)
                .appendQueryParameter(Constants.APP_ID_PARAM, Constants.APP_ID)
                .build();
        mView.showProgress();
        networkRequestHelper.requestString(uri.toString(), null, Request.Method.GET);
    }

    //API Error
    public void fetchImage(String imageId) {
        Uri uri  = Uri.parse(Constants.OPEN_WEATHER_IMAGE_API).buildUpon()
                .appendPath(imageId + Constants.PNG_EXTENSION)
                .build();
        mView.showProgress();
        networkRequestHelper.requestImage(uri.toString(), 300, 200, ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_8888);
    }

    public void onDestroy() {
        if (mView != null) {
            mView = null;
        }
    }

}
