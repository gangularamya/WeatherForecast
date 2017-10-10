package com.demo.weatherforecast.util;

/**
 * Created by ramya on 10/9/17.
 */

public class Constants {

    private Constants(){
        //private constructor for preventing object creation
    }

    public static final String OPEN_WEATHER_API = "http://api.openweathermap.org/data/2.5/weather";
    public static final String OPEN_WEATHER_IMAGE_API = "http://openweathermap.org/img/w/";
    public static final String WEATHER_FORECAST_PARAM = "q";
    public static final String UNITS_PARAM = "units";
    public static final String METRICS_PARAM = "metric";
    public static final String APP_ID_PARAM = "APPID";
    public static final String APP_ID = "976535ec83c4aba2eae773b96f0c6986";
    public static final String PNG_EXTENSION = ".png";
    public static final String PREFS_FILE_NAME = "WeatherForecastPreferances";
    public static final String CITY = "city";

}
