package com.example.building.base;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

import com.blankj.utilcode.util.StringUtils;
import com.example.building.R;
import com.example.building.config.LocalSaveData;
import com.gyf.immersionbar.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

import me.jessyan.autosize.AutoSizeCompat;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

public abstract class BaseActivity<V extends ViewDataBinding> extends SwipeBackActivity {
    public Activity activity;
    public V dataBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        initLang();
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, setViewId());
        activity = this;
        setViewId();
        initView();
        setListener();
    }

    private void initLang() {
        String lang = LocalSaveData.getInstance().getLang();
        if (StringUtils.isEmpty(lang)) {
            Locale locale;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                locale = getResources().getConfiguration().getLocales().get(0);
            } else {
                locale = getResources().getConfiguration().locale;
            }
            String systemLang = locale.getLanguage() + "-" + locale.getCountry();
            if (systemLang.contains("TW") || systemLang.contains("HK")) {
                setLang(LocalSaveData.TAG.LANG_TW);
            } else if (systemLang.contains(LocalSaveData.TAG.LANG_EN)) {
                setLang(LocalSaveData.TAG.LANG_EN);
            } else {
                setLang(LocalSaveData.TAG.LANG_CN);
            }
        } else {
            setLang(lang);
        }
    }

    public void registerEventMsg() {
        EventBus.getDefault().register(this);
    }

    protected abstract int setViewId();

    protected abstract void initView();

    protected abstract void setListener();

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    public void setLang(String lang) {
        LocalSaveData.getInstance().saveLang(lang);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        if (lang.equals(LocalSaveData.TAG.LANG_CN)) {
            config.locale = Locale.CHINESE;
        } else if (lang.equals(LocalSaveData.TAG.LANG_TW)) {
            config.locale = Locale.TAIWAN;
        } else {
            config.locale = Locale.ENGLISH;
        }
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(config, dm);
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    @Override
    public Resources getResources() {
        //需要升级到 v1.1.2 及以上版本才能使用 AutoSizeCompat
        AutoSizeCompat.autoConvertDensity(super.getResources(), 700
                , false);//如果有自定义需求就用这个方法
        return super.getResources();
    }
}
