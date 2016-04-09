package com.peerless2012.qingniantuzhai.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.StyleRes;
import android.text.format.DateUtils;

import com.peerless2012.qingniantuzhai.R;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/9 19:25
 * @Version V1.0
 * @Description : SharedPreference工具类
 */
public class SPUtils {

    private final static String CONFIG = "config";
    private final static String HOME_LAST_LOAD = "home_last_load";
    private final static String APP_THEME = "app_theme";

    private static volatile SPUtils sInst = null;  // <<< 这里添加了 volatile

    private  SharedPreferences mPreferences;

    private SPUtils(Context context) {
        mPreferences = context.getApplicationContext().getSharedPreferences(CONFIG, 0);
    }

    public static SPUtils getInstance(Context context) {
        SPUtils inst = sInst;  // <<< 在这里创建临时变量
        if (inst == null) {
            synchronized (SPUtils.class) {
                inst = sInst;
                if (inst == null) {
                    inst = new SPUtils(context);
                    sInst = inst;
                }
            }
        }
        return inst;  // <<< 注意这里返回的是临时变量
    }

    public boolean isTodaysFirst(){
        long lastTime = mPreferences.getLong(HOME_LAST_LOAD, System.currentTimeMillis());
        return DateUtils.isToday(lastTime - 6 * 60 * 60 * 1000);
    }

    public void restoreTodaysFirst(){
        mPreferences.edit().putLong(HOME_LAST_LOAD, System.currentTimeMillis()).apply();
    }

    public int getTheme(){
        return mPreferences.getInt(APP_THEME , R.style.AppTheme_Light);
    }

    public void restoreTheme(@StyleRes int style){
        mPreferences.edit().putInt(APP_THEME,style).apply();
    }

    public boolean isDayMode(){
        return mPreferences.getInt(APP_THEME , R.style.AppTheme_Light) == R.style.AppTheme_Light;
    }
}


