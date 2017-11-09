package com.demo.weatherforecast.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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

import com.demo.weatherforecast.R;
import com.demo.weatherforecast.data.sharedpreferences.SharedPreferenceHelper;
import com.demo.weatherforecast.model.WeatherData;
import com.demo.weatherforecast.util.Constants;
import com.demo.weatherforecast.util.DateUtil;
import com.demo.weatherforecast.util.GeneralUtil;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ramya on 10/9/17.
 */

public class MainFragment extends Fragment implements MainFragmentView{
    @BindView(R.id.city)
    TextView mCityView;

    @BindView(R.id.temp)
    TextView mTempView;

    @BindView(R.id.mintemp)
    TextView mMinTempView;

    @BindView(R.id.maxtemp)
    TextView mMaxTempView;

    @BindView(R.id.humidity)
    TextView mHumidityView;

    @BindView(R.id.pressure)
    TextView mPressureView;

    @BindView(R.id.sunrise)
    TextView mSunRiseView;

    @BindView(R.id.sunset)
    TextView mSunSetView;

    @BindView(R.id.root_container)
    LinearLayout mRootContainer;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.temp_image)
    ImageView mTempImage;

    @BindView(R.id.currentTime)
    TextView mCurrentTime;

    @BindView(R.id.last_updated_time)
    TextView mLastUpdatedTime;

    MainFragmentPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this, view);
        mCurrentTime.setText(DateUtil.formatDateToDisplay(Calendar.getInstance().getTime()));
        setHasOptionsMenu(true);
        checkSharedPrefs();
        presenter = new MainFragmentPresenter(getActivity(), this);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint(getString(R.string.enter_city_name));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.fetchData(query);
                showProgress();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void showProgress() {
        GeneralUtil.showProgress(mProgressBar, getActivity().getWindow());
    }

    @Override
    public void hideProgress() {
        GeneralUtil.hideProgress(mProgressBar, getActivity().getWindow());
    }

    @Override
    public void checkSharedPrefs() {
        String city = SharedPreferenceHelper.getPreference(getActivity(), Constants.CITY);
        if (!TextUtils.isEmpty(city)) {
            presenter.fetchData(city);
            showProgress();
        }
    }

    //Set Text Views with result
    @Override
    public void updateViews(WeatherData weatherData) {
        presenter.fetchImage(weatherData.getWeather().get(0).getIcon());
        mCurrentTime.setText(DateUtil.formatDateToDisplay(Calendar.getInstance().getTime()));
        mRootContainer.setVisibility(View.VISIBLE);
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

    @Override
    public void setImage(Bitmap responseObject) {
        mTempImage.setImageBitmap(responseObject);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}



