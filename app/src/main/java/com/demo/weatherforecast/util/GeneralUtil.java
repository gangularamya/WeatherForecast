package com.demo.weatherforecast.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

/**
 * Created by ramya on 10/10/17.
 * Util class to reuse the methods throughout the application
 */

public class GeneralUtil {

    private GeneralUtil(){
        //private constructor for preventing object creation
    }

    /**
     * Public method to determine if Device is connected to the internet.
     *
     * @param context context of the caller.
     */
    public static boolean isOnline(@NonNull Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            }
        }
        return false;
    }

    /**
     * shows progress bar to the user and disables interaction.
     *
     * @param progressBar progressbar to be shown on the screen
     * @param window window for which interaction needs to be disabled.
     */
    public static void showProgress(ProgressBar progressBar, Window window) {
        progressBar.setVisibility(View.VISIBLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    /**
     * hides progress bar to the user and enables interaction.
     *
     * @param progressBar progressbar to hide from the screen
     * @param window window for which interaction needs to be enabled.
     */
    public static void hideProgress(ProgressBar progressBar, Window window) {
        progressBar.setVisibility(View.GONE);
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
