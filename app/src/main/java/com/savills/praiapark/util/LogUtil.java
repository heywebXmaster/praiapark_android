package com.savills.praiapark.util;

import android.util.Log;

import com.savills.praiapark.config.AppConfig;

public class LogUtil {

    public static void d(String s) {
        d(AppConfig.LOG_TAG, s);
    }

    public static void d(String tag, String s) {
        if (AppConfig.ISDEBUG) {
            Log.d(tag, s);
        }
    }

    public static void i(String s) {
        i(AppConfig.LOG_TAG, s);
    }

    public static void i(String tag, String s) {
        if (AppConfig.ISDEBUG) {
            Log.i(tag, s);
        }
    }

    public static void w(String s) {
        w(AppConfig.LOG_TAG, s);
    }

    public static void w(String tag, String s) {
        if (AppConfig.ISDEBUG) {
            Log.w(tag, s);
        }
    }

    public static void e(String s) {
        e(AppConfig.LOG_TAG, s);
    }

    public static void e(String tag, String s) {
        if (AppConfig.ISDEBUG) {
            Log.e(tag, s);
        }
    }
}
