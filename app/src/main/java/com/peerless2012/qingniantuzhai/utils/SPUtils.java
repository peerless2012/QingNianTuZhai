package com.peerless2012.qingniantuzhai.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.text.format.DateUtils;

import java.util.Calendar;
import java.util.Locale;
import java.util.PriorityQueue;

/**
 * author peerless2012
 * 2016/1/23 21:45
 */
public class SPUtils {
    private final static String CONFIG = "config";
    private final static String HOME_LAST_LOAD = "home_last_load";

    public static boolean isTodaysFirst(Context context){
        SharedPreferences preferences = context.getSharedPreferences(CONFIG, 0);
        long lastTime = preferences.getLong(HOME_LAST_LOAD, System.currentTimeMillis());
        return DateUtils.isToday(lastTime - 6 * 60 * 60 * 1000);
    }

    public static void restoreTodaysFirst(Context context){
        SharedPreferences preferences = context.getSharedPreferences(CONFIG, 0);
        preferences.edit().putLong(HOME_LAST_LOAD, System.currentTimeMillis()).apply();
    }
}
