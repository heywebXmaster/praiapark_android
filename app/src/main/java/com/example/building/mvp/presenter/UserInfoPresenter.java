package com.example.building.mvp.presenter;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.building.bean.BaseEntity;
import com.example.building.bean.ProfileBean;
import com.example.building.config.HttpConfig;
import com.example.building.config.LocalSaveData;
import com.example.building.mvp.contract.UserInfoContract;
import com.example.building.net.BaseObserver;
import com.example.building.net.RetrofitFactory;
import com.example.building.util.LogUtil;
import com.example.building.util.MD5Tool;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserInfoPresenter implements UserInfoContract.UserInfoPresenter {

    UserInfoContract.UserInfoView infoView;

    public UserInfoPresenter(UserInfoContract.UserInfoView infoView) {
        this.infoView = infoView;
    }

    @Override
    public void getProfile() {
        infoView.showLoading();
        RetrofitFactory.getInstence().API().getProfile(LocalSaveData.getInstance().getUserName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ProfileBean>() {

                    @Override
                    protected void onSuccees(BaseEntity<ProfileBean> t) {
                        infoView.showProfile(t.getResult());
                    }

                    @Override
                    protected void onFailure(Throwable e) {

                    }

                    @Override
                    protected void onFinish() {
                        infoView.hideLoading();
                    }
                });
    }

    @Override
    public void updateProfile(String nickname, String phoneNumber, String avatar) {
        infoView.showLoading();
        MultipartBody.Part body = null;
        if (!StringUtils.isEmpty(avatar)) {
            File file = new File(avatar);
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("image/png"), file);
            body = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
        }
        StringBuffer signStr = new StringBuffer();
        signStr.append("appId").append("=").append(HttpConfig.APP_ID);
        signStr.append("&lang").append("=").append(LocalSaveData.getInstance().getLang());
        if (!StringUtils.isEmpty(nickname)) {
            signStr.append("&nickname").append("=").append(EncodeUtils.urlEncode(nickname));
        }
        if (!StringUtils.isEmpty(phoneNumber)) {
            signStr.append("&phoneNumber").append("=").append(phoneNumber);
        }
        signStr.append("&username").append("=").append(LocalSaveData.getInstance().getUserName());
        signStr.append("&appSecret").append("=").append(HttpConfig.APP_SECRET);
        LogUtil.e(signStr.toString());
        String firstMd5 = MD5Tool.md5(signStr.toString());
        String upperCase = firstMd5.toUpperCase();
        String finalMd5 = MD5Tool.md5(upperCase).toUpperCase();
        LogUtil.e(firstMd5);
        LogUtil.e(upperCase);
        LogUtil.e(finalMd5);
        RequestBody body1 = RequestBody.create(MediaType.parse("application/form-data; charset=UTF-8"), HttpConfig.APP_ID);
        RequestBody body2 = RequestBody.create(MediaType.parse("application/form-data; charset=UTF-8"), LocalSaveData.getInstance().getLang());
        RequestBody body3 = null;
        if (!StringUtils.isEmpty(nickname)) {
            body3 = RequestBody.create(MediaType.parse("application/form-data; charset=UTF-8"), nickname);
        }
        RequestBody body4 = null;
        if (!StringUtils.isEmpty(phoneNumber)) {
            body4 = RequestBody.create(MediaType.parse("application/form-data; charset=UTF-8"), phoneNumber);
        }

        RequestBody body5 = RequestBody.create(MediaType.parse("application/form-data; charset=UTF-8"), LocalSaveData.getInstance().getUserName());
        RequestBody body6 = RequestBody.create(MediaType.parse("application/form-data; charset=UTF-8"), finalMd5);
        Map<String, RequestBody> params = new HashMap<>();
        params.put("appId", body1);
        params.put("lang", body2);
        if (!StringUtils.isEmpty(nickname)) {
            params.put("nickname", body3);
        }
        if (!StringUtils.isEmpty(phoneNumber)) {
            params.put("phoneNumber", body4);
        }
        params.put("username", body5);
        params.put("sign", body6);
        RetrofitFactory.getInstence().API().updateProfile(params,
                body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>() {

                    @Override
                    protected void onSuccees(BaseEntity<String> t) {
                        infoView.showToast(t.getErrorMsg());
                        infoView.updateProfileSuccess();
                    }

                    @Override
                    protected void onFailure(Throwable e) {

                    }

                    @Override
                    protected void onFinish() {
                        infoView.hideLoading();
                    }
                });

//        RetrofitFactory.getInstence().API().updateProfile(LocalSaveData.getInstance().getUserName(),
//                EncodeUtils.urlEncode(nickname),
//                phoneNumber, avatar)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<String>() {
//
//                    @Override
//                    protected void onSuccees(BaseEntity<String> t) {
//                        infoView.updateProfileSuccess();
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e) {
//
//                    }
//
//                    @Override
//                    protected void onFinish() {
//                        infoView.hideLoading();
//                    }
//                });
    }


    @Override
    public void resetPassword(String oldPassword, String newPassword) {
        infoView.showLoading();
        RetrofitFactory.getInstence().API().resetPassword(LocalSaveData.getInstance().getUserName(),
                MD5Tool.md5(oldPassword),
                MD5Tool.md5(newPassword))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>() {

                    @Override
                    protected void onSuccees(BaseEntity<String> t) {
                        LocalSaveData.getInstance().setLogin(false);
                        infoView.showToast(t.getErrorMsg());
                        infoView.resetPasswordSuccess();
                    }

                    @Override
                    protected void onFailure(Throwable e) {

                    }

                    @Override
                    protected void onFinish() {
                        infoView.hideLoading();
                    }
                });
    }
}
