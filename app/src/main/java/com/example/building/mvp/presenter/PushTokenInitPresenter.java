package com.example.building.mvp.presenter;

import com.example.building.bean.BaseEntity;
import com.example.building.config.LocalSaveData;
import com.example.building.mvp.contract.PushTokenInitContract;
import com.example.building.net.BaseObserver;
import com.example.building.net.RetrofitFactory;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PushTokenInitPresenter implements PushTokenInitContract.PushTokenInitPresenter {

    @Override
    public void uploadPushToken(String regId) {
        RetrofitFactory.getInstence().API().initToken(
                LocalSaveData.getInstance().getUserName(),
                regId,
                1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>() {

                    @Override
                    protected void onSuccees(BaseEntity<String> t) {

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
