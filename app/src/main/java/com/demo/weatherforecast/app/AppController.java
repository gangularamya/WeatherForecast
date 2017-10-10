package com.demo.weatherforecast.app;

import android.app.Application;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;

/**
 * Created by ramya on 10/9/17.
 */

public class AppController extends Application{

    private static final String TAG = AppController.class.getSimpleName();
    private static final int SOCKET_TIMEOUT = 10000;
    private RequestQueue mRequestQueue;
    private static AppController mInstance;

    private static void setAppContext(AppController context){
        mInstance = context;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        setAppContext(this);
        registerActivityLifecycleCallbacks(new AppLifeCycleHandler());
    }

    public static synchronized AppController getInstance(){
        return mInstance;
    }

    private RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        RetryPolicy policy = new DefaultRetryPolicy(SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(policy);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(){
        if(mRequestQueue != null){
            mRequestQueue.cancelAll(TAG);
        }
    }
}
