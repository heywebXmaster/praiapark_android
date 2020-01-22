package com.savills.praiapark.jpush;


import android.support.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.savills.praiapark.mvp.presenter.PushTokenInitPresenter;
import com.savills.praiapark.util.LogUtil;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        LogUtil.e("fcm onNewToken:" + token);
        PushTokenInitPresenter initPresenter = new PushTokenInitPresenter();
        initPresenter.uploadPushToken(token);
    }
}
