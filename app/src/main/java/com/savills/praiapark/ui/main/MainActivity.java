package com.savills.praiapark.ui.main;

import com.google.firebase.messaging.FirebaseMessaging;
import com.savills.praiapark.R;
import com.savills.praiapark.base.BaseActivity;
import com.savills.praiapark.config.LocalSaveData;
import com.savills.praiapark.databinding.ActivityMainBinding;

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
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
    }

    @Override
    protected void setListener() {
    }

}
