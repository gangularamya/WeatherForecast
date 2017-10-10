package com.demo.weatherforecast.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by ramya on 10/9/17.
 */

public class AppLifeCycleHandler implements Application.ActivityLifecycleCallbacks {
    private  static int started;
    private  static int stopped;

    private static void incrementStarted(){
        ++started;
    }

    private static void incrementStopped(){
        ++stopped;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //default implementation ignored
    }

    @Override
    public void onActivityStarted(Activity activity) {
        incrementStarted();
    }

    @Override
    public void onActivityResumed(Activity activity) {
        //default implementation ignored
    }

    @Override
    public void onActivityPaused(Activity activity) {
        //default implementation ignored
    }

    @Override
    public void onActivityStopped(Activity activity) {
        incrementStopped();
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        //default implementation ignored
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        //default implementation ignored
    }
}
