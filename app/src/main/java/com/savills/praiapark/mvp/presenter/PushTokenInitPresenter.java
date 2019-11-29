package com.savills.praiapark.mvp.presenter;

import android.util.Log;

import com.savills.praiapark.bean.BaseEntity;
import com.savills.praiapark.config.LocalSaveData;
import com.savills.praiapark.mvp.contract.PushTokenInitContract;
import com.savills.praiapark.net.BaseObserver;
import com.savills.praiapark.net.RetrofitFactory;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PushTokenInitPresenter implements PushTokenInitContract.PushTokenInitPresenter {

    @Override
    public void uploadPushToken(String regId) {
        Log.e("PushTokenInitPresenter", "regId: " + regId);

        RetrofitFactory.getInstence().API().initToken(
                LocalSaveData.getInstance().getUserName(),
                regId,
                1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>() {

                    @Override
                    protected void onSuccees(BaseEntity<String> t) {
                        Log.i("XXXXX",t.toString());
                    }

                    @Override
                    protected void onFailure(Throwable e) {

                    }

                    @Override
                    protected void onFinish() {

                    }
                });
    }
}
