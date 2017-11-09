package com.demo.weatherforecast.data.webservices;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.demo.weatherforecast.R;
import com.demo.weatherforecast.app.AppController;
import com.demo.weatherforecast.util.GeneralUtil;

/**
 * Created by ramya on 11/8/17.
 */

public class NetworkRequestHelper {
    private static final String TAG = NetworkRequestHelper.class.getSimpleName();
    private Context context;
    private OnRequestCompletedListener mRequestCompletedListener;
    private OnImageRequestListener mImageRequestListener;

    public NetworkRequestHelper(Context context) {
        this.context = context;
    }


    /**
     * Used to call web service and get response as JSON using post method.
     *
     * @param context  - context of the activity.
     * @param callback - The callback reference.
     */
    public NetworkRequestHelper(Context context, OnRequestCompletedListener callback, OnImageRequestListener imageCallback) {
        mRequestCompletedListener = callback;
        mImageRequestListener = imageCallback;
        this.context = context;
    }

    /**
     * Request String response from the Web API.
     *
     * @param webserviceUrl the String refers the web service URL.
     * @param requestParams the list of parameters in byte array.
     * @param webMethod     the integer indicates the web method.
     */
    public void requestString(final String webserviceUrl,
                              final byte[] requestParams, final int webMethod) {
        if(GeneralUtil.isOnline(context)) {
            StringRequest stringRequest = new StringRequest(webMethod,
                    webserviceUrl, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    mRequestCompletedListener.onRequestCompleted(
                            webserviceUrl, true, response, null);
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "Error: " + error.getMessage());
                }
            }) {

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    final byte[] body = requestParams;
                    if (body != null) {
                        return body;
                    }
                    return super.getBody();
                }
            };
            // Adding String request to request queue
            AppController.getInstance().addToRequestQueue(stringRequest, webserviceUrl);
        }else{
            Toast.makeText(context,context.getString(R.string.network_error_string),Toast.LENGTH_LONG).show();
        }
    }

    //Image Request
    public void requestImage(final String webserviceUrl,
                             int maxWidth, int maxHeight, ImageView.ScaleType scaleType,
                             final Bitmap.Config decodeConfig) {
        if(GeneralUtil.isOnline(context)) {
            ImageRequest imageRequest = new ImageRequest(webserviceUrl,
                    new Response.Listener<Bitmap>() {

                        @Override
                        public void onResponse(Bitmap response) {
                            mImageRequestListener.onImageRequestCompleted(
                                    webserviceUrl, true, response, null);
                        }
                    }, maxWidth, maxHeight, scaleType, decodeConfig, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "Error: " + error.getMessage());
                }
            });
            // Adding String request to request queue
            AppController.getInstance().addToRequestQueue(imageRequest, webserviceUrl);
        }else{
            Toast.makeText(context,context.getString(R.string.network_error_string),Toast.LENGTH_LONG).show();
        }
    }


    /**
     * A callback interface indicates the status about the completion of HTTP
     * request.
     */
    public interface OnRequestCompletedListener {
        /**
         * Called when the String request has been completed.
         *
         * @param requestName  the String refers the request name
         * @param status       the status of the request either success or failure
         * @param response     the String response returns from the Webservice. It may be
         *                     null if request failed.
         * @param errorMessage the String refers the error message when request failed to
         *                     get the response
         */
        void onRequestCompleted(String requestName, boolean status,
                                String response, String errorMessage);

    }

    public interface OnImageRequestListener{
        /**
         * Called when the Image request has been completed.
         *
         * @param requestName  the String refers the request name
         * @param status       the status of the request either success or failure
         * @param response     the Image response returns from the Webservice. It may be
         *                     null if request failed.
         * @param errorMessage the String refers the error message when request failed to
         *                     get the response
         */

        void onImageRequestCompleted(String requestName, boolean status,
                                     Bitmap response, String errorMessage);

    }
}
