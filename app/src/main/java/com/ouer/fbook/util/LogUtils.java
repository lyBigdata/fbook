package com.ouer.fbook.util;

import android.util.Log;

import com.ouer.fbook.BuildConfig;


/**
 * Created by ouer on 2018/2/27.
 */

public class LogUtils {
    private static boolean isLog = BuildConfig.DEBUG;

    public static void e(String tag, String msg) {
        if (isLog) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, Exception ex) {
        if (isLog) {
            Log.e(tag, ex.getMessage());
        }
    }

    public static void d(String tag, String msg) {
        if (isLog) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isLog) {
            Log.i(tag, msg);
        }
    }

    public static void i(String msg) {
        if (isLog) {
            Log.i("default", msg);
        }
    }
}
