package com.example.building.mvp.presenter;

import android.annotation.SuppressLint;

import com.example.building.bean.BaseEntity;
import com.example.building.bean.ProfileBean;
import com.example.building.config.LocalSaveData;
import com.example.building.mvp.BasePresenter;
import com.example.building.mvp.contract.LoginContract;
import com.example.building.net.BaseObserver;
import com.example.building.net.RetrofitFactory;
import com.example.building.util.MD5Tool;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter implements LoginContract.LoginPresenter {

    LoginContract.LoginView loginView;

    public LoginPresenter(LoginContract.LoginView loginView) {
        this.loginView = loginView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void register(String username, String password, String householderCode) {
        loginView.showLoading();
        RetrofitFactory.getInstence().API().register(username, MD5Tool.md5(password), householderCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ProfileBean>() {

                    @Override
                    protected void onSuccees(BaseEntity<ProfileBean> t) {
                        loginView.showToast(t.getErrorMsg());
                        setUserInfo(t.getResult());
                        loginView.registerSuccess();
                    }

                    @Override
                    protected void onFailure(Throwable e) {

                    }

                    @Override
                    protected void onFinish() {
                        loginView.hideLoading();
                    }
                });


    }

    @Override
    public void login(String username, String password) {
        loginView.showLoading();
        RetrofitFactory.getInstence().API().login(username, MD5Tool.md5(password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ProfileBean>() {

                    @Override
                    protected void onSuccees(BaseEntity<ProfileBean> t) {
                        loginView.showToast(t.getErrorMsg());
                        setUserInfo(t.getResult());
                        loginView.loginSuccess();
                    }

                    @Override
                    protected void onFailure(Throwable e) {

                    }

                    @Override
                    protected void onFinish() {
                        loginView.hideLoading();
                    }
                });
    }

    @Override
    public void forget(String username, String password) {
        loginView.showLoading();
        RetrofitFactory.getInstence().API().forget(username, MD5Tool.md5(password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>() {

                    @Override
                    protected void onSuccees(BaseEntity<String> t) {
                        loginView.showToast(t.getErrorMsg());
                        loginView.forgetSuccess();
                    }

                    @Override
                    protected void onFailure(Throwable e) {

                    }

                    @Override
                    protected void onFinish() {
                        loginView.hideLoading();
                    }
                });
    }

    private void setUserInfo(ProfileBean profileBean){
        LocalSaveData.getInstance().setLogin(true);
        LocalSaveData.getInstance().setUserName(profileBean.getUsername());
        LocalSaveData.getInstance().setHouseHolderCode(profileBean.getHouseholderCode());
    }

}
