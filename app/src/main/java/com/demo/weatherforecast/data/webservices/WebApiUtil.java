package com.demo.weatherforecast.data.webservices;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
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
    private ImageApiListener mImageListener;

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
     * Interface definition for a callback to be invoked when API call response is received.
     */
    public interface ImageApiListener {
        /**
         * API returned successfully.
         */
        void onSuccess(Bitmap response);

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
     * Request for Weather Forecast Details
     *
     * @param listener listener on which callback methods are called
     */
    public void getWeatherForecast(WebApiListener listener, String city) {
        mListener = listener;
        Uri builtUri = Uri.parse(Constants.OPEN_WEATHER_API).buildUpon()
                .appendQueryParameter("q", city)
                .appendQueryParameter("units", "metric")
                .appendQueryParameter("APPID", "976535ec83c4aba2eae773b96f0c6986")
                .build();
        GetRequest weatherForecastRequest = new GetRequest(Request.Method.GET, builtUri.toString(), successListener(), errorListener());

        weatherForecastRequest.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(weatherForecastRequest);

    }


    /**
     * Request for Image
     *
     * @param listener listener on which callback methods are called
     */
    public void getWeatherImage(ImageApiListener listener, String imageId) {
        mImageListener = listener;
        Uri builtUri = Uri.parse(Constants.OPEN_WEATHER_IMAGE_API).buildUpon()
                .appendPath(imageId +".png")
                .build();
        ImageRequest imageRequest = new ImageRequest(builtUri.toString(), imageOnSuccessListener(), 300, 200, ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_8888, imageOnErrorListener());
        imageRequest.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(imageRequest);

    }

    //Image Success Listener
    private Response.Listener<Bitmap> imageOnSuccessListener() {
        return new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                mImageListener.onSuccess(response);
            }
        };
    }

    //Error Listener
    private Response.ErrorListener imageOnErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                mImageListener.onFailure(error);
            }
        };
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
