package com.demo.weatherforecast.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.demo.weatherforecast.R;
import com.demo.weatherforecast.data.webservices.WebApiUtil;
import com.demo.weatherforecast.model.WeatherData;
import com.google.gson.Gson;

/**
 * Created by ramya on 10/9/17.
 */

public class MainFragment extends Fragment {
    TextView cityView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);
        cityView = view.findViewById(R.id.city);
        return view;
    }

    private WebApiUtil.WebApiListener getWeatherForecastListener = new WebApiUtil.WebApiListener() {

        @Override
        public void onSuccess(String response) {
            Log.d("Success",response);
            WeatherData weatherData = new Gson().fromJson(response, WeatherData.class);
            cityView.setText(weatherData.getName());

        }


        @Override
        public void onFailure(VolleyError error) {
            Log.d("error","Failure"+error.getMessage());
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item =menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setQueryHint(getString(R.string.enter_city_name));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                WebApiUtil.getInstance().getWeatherForecast(getWeatherForecastListener,query);
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



