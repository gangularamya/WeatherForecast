package com.demo.weatherforecast.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.demo.weatherforecast.R;
import com.demo.weatherforecast.data.sharedpreferences.SharedPreferenceHelper;
import com.demo.weatherforecast.data.webservices.WebApiUtil;
import com.demo.weatherforecast.model.WeatherData;
import com.demo.weatherforecast.util.Constants;
import com.demo.weatherforecast.util.DateUtil;
import com.demo.weatherforecast.util.GeneralUtil;
import com.google.gson.Gson;

import java.util.Calendar;

/**
 * Created by ramya on 10/9/17.
 */

public class MainFragment extends Fragment {
    private TextView mCityView;
    private TextView mTempView;
    private TextView mMinTempView;
    private TextView mMaxTempView;
    private TextView mHumidityView;
    private TextView mPressureView;
    private TextView mSunRiseView;
    private TextView mSunSetView;
    private LinearLayout mRootContainer;
    private ProgressBar mProgressBar;
    private ImageView mTempImage;
    private TextView mCurrentTime;
    private TextView mLastUpdatedTime;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);
        mCityView = view.findViewById(R.id.city);
        mTempView = view.findViewById(R.id.temp);
        mMinTempView = view.findViewById(R.id.mintemp);
        mMaxTempView = view.findViewById(R.id.maxtemp);
        mHumidityView = view.findViewById(R.id.humidity);
        mPressureView = view.findViewById(R.id.pressure);
        mSunRiseView = view.findViewById(R.id.sunrise);
        mSunSetView = view.findViewById(R.id.sunset);
        mRootContainer = view.findViewById(R.id.root_container);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mTempImage = view.findViewById(R.id.temp_image);
        mCurrentTime = view.findViewById(R.id.currentTime);
        mCurrentTime.setText(DateUtil.formatDateToDisplay(Calendar.getInstance().getTime()));
        mLastUpdatedTime = view.findViewById(R.id.last_updated_time);
        checkSharedPref();
        return view;
    }

    private void checkSharedPref(){
        String city = SharedPreferenceHelper.getPreference(getActivity(), Constants.CITY);
        if(!TextUtils.isEmpty(city)){
            sendWeatherForecastRequest(city);
        }
    }

    private void sendWeatherForecastRequest(String query) {
        SharedPreferenceHelper.setPreference(getActivity(), Constants.CITY,query);
        if (GeneralUtil.isOnline(getActivity())) {
            GeneralUtil.showProgress(mProgressBar, getActivity().getWindow());
            WebApiUtil.getInstance().getWeatherForecast(getWeatherForecastListener, query);
        } else {
            GeneralUtil.hideProgress(mProgressBar, getActivity().getWindow());
            Snackbar.make(getActivity().findViewById(R.id.root_layout), getString(R.string.network_error_string), Snackbar.LENGTH_SHORT);
        }

    }

    private WebApiUtil.WebApiListener getWeatherForecastListener = new WebApiUtil.WebApiListener() {

        @Override
        public void onSuccess(String response) {
            Log.d("Success", response);
            GeneralUtil.hideProgress(mProgressBar, getActivity().getWindow());
            mCurrentTime.setText(DateUtil.formatDateToDisplay(Calendar.getInstance().getTime()));
            mRootContainer.setVisibility(View.VISIBLE);

            WeatherData weatherData = new Gson().fromJson(response, WeatherData.class);
            sendImageRequest(weatherData.getWeather().get(0).getIcon());

            mCityView.setText(weatherData.getName());
            mTempView.setText(getString(R.string.temp_string, String.valueOf(weatherData.getMain().getTemp())));
            mMinTempView.setText(getString(R.string.min_temp, String.valueOf(weatherData.getMain().getTemp_min())));
            mMaxTempView.setText(getString(R.string.max_temp, String.valueOf(weatherData.getMain().getTemp_max())));
            mHumidityView.setText(getString(R.string.humidity, String.valueOf(weatherData.getMain().getHumidity())));
            mPressureView.setText(getString(R.string.pressure, String.valueOf(weatherData.getMain().getPressure())));
            mSunRiseView.setText(getString(R.string.sunrise, String.valueOf(DateUtil.getHourMinsTime(weatherData.getSys().getSunrise()))));
            mSunSetView.setText(getString(R.string.sunset, String.valueOf(DateUtil.getHourMinsTime(weatherData.getSys().getSunset()))));
            mLastUpdatedTime.setText(getString(R.string.last_updated_time, String.valueOf(DateUtil.getLastUpdated(weatherData.getDt()))));
        }

        private void sendImageRequest(String icon) {
            if (GeneralUtil.isOnline(getActivity())) {
                GeneralUtil.showProgress(mProgressBar, getActivity().getWindow());
                WebApiUtil.getInstance().getWeatherImage(getWeatherImageListener, icon);
            } else {
                GeneralUtil.hideProgress(mProgressBar, getActivity().getWindow());
                Snackbar.make(getActivity().findViewById(R.id.root_layout), getString(R.string.network_error_string), Snackbar.LENGTH_SHORT);
            }

        }

        @Override
        public void onFailure(VolleyError error) {
            GeneralUtil.hideProgress(mProgressBar, getActivity().getWindow());
            Log.d("error", "Failure" + error.getMessage());
            NetworkResponse networkResponse = error.networkResponse;
            if (error instanceof TimeoutError || error instanceof NetworkError) {
                Snackbar.make(getActivity().findViewById(R.id.root_layout), getString(R.string.network_error_string), Snackbar.LENGTH_SHORT);
            } else if (networkResponse != null) {
                Snackbar.make(getActivity().findViewById(R.id.root_layout), getString(R.string.generic_error_string), Snackbar.LENGTH_SHORT);
            }
        }
    };

    private WebApiUtil.ImageApiListener getWeatherImageListener = new WebApiUtil.ImageApiListener() {

        @Override
        public void onSuccess(Bitmap response) {
            GeneralUtil.hideProgress(mProgressBar, getActivity().getWindow());
            mTempImage.setImageBitmap(response);
        }

        @Override
        public void onFailure(VolleyError error) {
            GeneralUtil.hideProgress(mProgressBar, getActivity().getWindow());
            Log.d("error", "Failure" + error.getMessage());
            NetworkResponse networkResponse = error.networkResponse;
            if (error instanceof TimeoutError || error instanceof NetworkError) {
                Snackbar.make(getActivity().findViewById(R.id.root_layout), getString(R.string.network_error_string), Snackbar.LENGTH_SHORT);
            } else if (networkResponse != null) {
                Snackbar.make(getActivity().findViewById(R.id.root_layout), getString(R.string.generic_error_string), Snackbar.LENGTH_SHORT);
            }
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint(getString(R.string.enter_city_name));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sendWeatherForecastRequest(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}



