package com.demo.weatherforecast.util;

import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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

    /*
    Returns relative Time
     */
    public static CharSequence getLastUpdated(long dateinMillis){
        long currentTime = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis();
        return DateUtils.getRelativeTimeSpanString(dateinMillis*1000L,currentTime,
                DateUtils.MINUTE_IN_MILLIS,
                DateUtils.FORMAT_NO_NOON);
    }

    /*
       Returns hour and min using time in millis
     */
    public static String getHourMinsTime(long dateinMillis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateinMillis*1000);
        return calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE);
    }
}
