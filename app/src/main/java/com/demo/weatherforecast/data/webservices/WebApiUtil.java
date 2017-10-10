package com.demo.weatherforecast.data.webservices;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.demo.weatherforecast.app.AppController;
import com.demo.weatherforecast.data.webservices.volley.requests.GetRequest;
import com.demo.weatherforecast.util.Constants;

/**
 * Created by ramya on 10/9/17.
 */

public class WebApiUtil {

    private static WebApiUtil sInstance;
    private static final String TAG = WebApiUtil.class.getSimpleName();
    private WebApiListener mListener;

    /**
     * Interface definition for a callback to be invoked when API call response is received.
     */
    public interface WebApiListener {
        /**
         * API returned successfully.
         */
        void onSuccess(String response);

        /**
         * API call failed.
         */
        void onFailure(VolleyError error);
    }


    /**
     * Factory method to get web util instance
     *
     * @return {@link WebApiUtil} object
     */
    @NonNull
    public static WebApiUtil getInstance() {

        if (sInstance == null) {
            sInstance = new WebApiUtil();
        }
        return sInstance;
    }

    /**
     * Request for updating paperless status
     *
     * @param listener listener on which callback methods are called
     */
    public void getWeatherForecast(WebApiListener listener, String city) {
        mListener = listener;
        Uri builtUri = Uri.parse(Constants.OPEN_WEATHER_API).buildUpon()
                .appendQueryParameter("q", city)
                .appendQueryParameter("APPID","976535ec83c4aba2eae773b96f0c6986")
                .build();
        GetRequest weatherForecastRequest = new GetRequest(Request.Method.GET, builtUri.toString(), successListener(), errorListener());

        weatherForecastRequest.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(weatherForecastRequest);

    }

    //Success Listener
    private Response.Listener<String> successListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mListener.onSuccess(response);
            }
        };
    }

    //Error Listener
    private Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                mListener.onFailure(error);
            }
        };
    }

}
