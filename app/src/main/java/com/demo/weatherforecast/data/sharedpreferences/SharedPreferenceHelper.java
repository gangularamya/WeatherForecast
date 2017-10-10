package com.demo.weatherforecast.data.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.demo.weatherforecast.util.Constants;

/**
 * Created by ramya on 10/10/17.
 */

public class SharedPreferenceHelper {

    private SharedPreferenceHelper() {

    }

    private static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(Constants.PREFS_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static boolean setPreference(Context context, String key, String value) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getPreference(Context context, String key) {
        return getSharedPreference(context).getString(key, null);
    }

}
