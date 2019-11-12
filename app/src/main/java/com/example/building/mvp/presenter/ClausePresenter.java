package com.example.building.mvp.presenter;

import com.example.building.bean.BaseEntity;
import com.example.building.bean.ClauseBean;
import com.example.building.config.LocalSaveData;
import com.example.building.mvp.contract.ClauseContract;
import com.example.building.net.BaseObserver;
import com.example.building.net.RetrofitFactory;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ClausePresenter implements ClauseContract.ClausePresenter {

    ClauseContract.ClauseView clauseView;

    public ClausePresenter(ClauseContract.ClauseView clauseView) {
        this.clauseView = clauseView;
    }

    @Override
    public void getKeysOfClause() {
        RetrofitFactory.getInstence().API().getClauseKeys(
                LocalSaveData.getInstance().getUserName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<ClauseBean>>() {

                    @Override
                    protected void onSuccees(BaseEntity<List<ClauseBean>> t) {
                        clauseView.getKeysOfClauseSuccess(t.getResult());
                    }

                    @Override
                    protected void onFailure(Throwable e) {

                    }

                    @Override
                    protected void onFinish() {
                    }
                });
    }

    @Override
    public void getClause(String key) {
        clauseView.showLoading();
        RetrofitFactory.getInstence().API().getClause(
                LocalSaveData.getInstance().getUserName(), key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ClauseBean>() {

                    @Override
                    protected void onSuccees(BaseEntity<ClauseBean> t) {
                        clauseView.showClause(t.getResult());
                    }

                    @Override
                    protected void onFailure(Throwable e) {

                    }

                    @Override
                    protected void onFinish() {
                        clauseView.hideLoading();
                    }
                });
    }
}
