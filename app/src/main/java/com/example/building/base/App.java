package com.example.building.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import android.util.Log;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;
import com.example.building.BuildConfig;
import com.example.building.R;
import com.example.building.config.LocalSaveData;
import com.example.building.util.LogUtil;
import com.liulishuo.filedownloader.FileDownloader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.Locale;

import cn.jpush.android.api.JPushInterface;
import me.jessyan.autosize.AutoSizeConfig;
import me.yokeyword.fragmentation.Fragmentation;

public class App extends Application {

    public static App instance;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
        Utils.init(context);
        AutoSizeConfig.getInstance().setCustomFragment(true);
        registerActivityLifecycle();
        FileDownloader.setup(context);
        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(false)
                .install();
    }

    public static App getInstance() {
        return instance;
    }

    public Context getContext() {
        return context;
    }

    public void initPush(){
        JPushInterface.setDebugMode(true);
        JPushInterface.init(instance);
    }


    private void registerActivityLifecycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                ActivityManager.getInstance().setCurrentActivity(activity);
                LogUtil.d(activity + ":onActivityCreated");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                LogUtil.d(activity + ":onActivityStarted");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                LogUtil.d(activity + ":onActivityResumed");
                ActivityManager.getInstance().setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                LogUtil.d(activity + ":onActivityPaused");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                LogUtil.d(activity + ":onActivityStopped");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                LogUtil.d(activity + ":onActivitySaveInstanceState");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                LogUtil.d(activity + ":onActivityDestroyed");
            }
        });
    }
}
