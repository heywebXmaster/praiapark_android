package com.savills.praiapark.net;

import android.util.Log;

import com.savills.praiapark.config.AppConfig;

import okhttp3.logging.HttpLoggingInterceptor;

public class InterceptorUtil {

    public static HttpLoggingInterceptor LogInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.w(AppConfig.LOG_TAG, "log: " + message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
    }
}
