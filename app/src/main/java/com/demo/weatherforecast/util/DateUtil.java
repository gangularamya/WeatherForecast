package com.demo.weatherforecast.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ramya on 10/10/17.
 */

public class DateUtil {

    private DateUtil() {
        //private constructor for preventing object creation
    }

    /*
    Displays date in E, MMM dd h:m a format
     */
    public static String formatDateToDisplay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("E, MMM dd h:mm a", Locale.US);
        return sdf.format(date);
    }
}
