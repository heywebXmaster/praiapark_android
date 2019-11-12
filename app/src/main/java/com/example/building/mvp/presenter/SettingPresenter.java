package com.example.building.mvp.presenter;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.EncodeUtils;
import com.example.building.bean.BaseEntity;
import com.example.building.bean.PdfBean;
import com.example.building.config.LocalSaveData;
import com.example.building.mvp.BasePresenter;
import com.example.building.mvp.contract.SettingContract;
import com.example.building.net.APIFunction;
import com.example.building.net.BaseObserver;
import com.example.building.net.RetrofitFactory;
import com.example.building.util.EnhancedCall;
import com.example.building.util.EnhancedCallback;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class SettingPresenter extends BasePresenter implements SettingContract.SettingPresenter {

    SettingContract.SettingView settingView;


    public SettingPresenter(SettingContract.SettingView settingView) {
        this.settingView = settingView;
    }

    @Override
    public void contactUs(String title, String content, String nickname, String phoneNumber) {
        settingView.showLoading();
        RetrofitFactory.getInstence().API().contactUs(
                LocalSaveData.getInstance().getUserName(),
                EncodeUtils.urlEncode("01-10-A-01"),
                EncodeUtils.urlEncode(title),
                EncodeUtils.urlEncode(content),
                EncodeUtils.urlEncode(nickname),
                EncodeUtils.urlEncode(phoneNumber))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>() {

                    @Override
                    protected void onSuccees(BaseEntity<String> t) {
                        settingView.showToast(t.getErrorMsg());
                        settingView.contactUsSuccess();
                    }

                    @Override
                    protected void onFailure(Throwable e) {

                    }

                    @Override
                    protected void onFinish() {
                        settingView.hideLoading();
                    }
                });

    }

    @Override
    public void getPdfs() {
        APIFunction service = getApiService();
        Call<BaseEntity<List<PdfBean>>> call = service.getPdfs(LocalSaveData.getInstance().getUserName());
        EnhancedCall<BaseEntity<List<PdfBean>>> enhancedCall = new EnhancedCall<>(call);
        enhancedCall.dataClassName(BaseEntity.class)
                .enqueue(new EnhancedCallback<BaseEntity<List<PdfBean>>>() {
                    @Override
                    public void onResponse(Call<BaseEntity<List<PdfBean>>> call, Response<BaseEntity<List<PdfBean>>> response) {
                        BaseEntity<List<PdfBean>> entity = response.body();
                        if (isSuccess(entity)) {
                            settingView.showPdfs(entity.getResult());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseEntity<List<PdfBean>>> call, Throwable t) {
                        settingView.showLoadError();
                    }

                    @Override
                    public void onGetCache(BaseEntity<List<PdfBean>> entity) {
                        List<PdfBean> list = JSON.parseArray(entity.getResult() + "", PdfBean.class);
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        settingView.showPdfs(list);

                    }
                });
    }

}
