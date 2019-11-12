package com.example.building.ui.main;

import com.example.building.R;
import com.example.building.base.BaseActivity;
import com.example.building.config.LocalSaveData;
import com.example.building.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected int setViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        setSwipeBackEnable(false);
        if (LocalSaveData.getInstance().isLogin()) {
            loadRootFragment(R.id.frameLayout, MainFragment.newInstant());
        } else {
            loadRootFragment(R.id.frameLayout, LoginFragment.newInstant());
        }
    }

    @Override
    protected void setListener() {
    }

}
