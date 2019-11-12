package com.example.building.mvp.presenter;

import com.alibaba.fastjson.JSON;
import com.example.building.bean.AroundInfoBean;
import com.example.building.bean.BaseEntity;
import com.example.building.bean.TrafficInfoBean;
import com.example.building.bean.UnitInfoBean;
import com.example.building.config.LocalSaveData;
import com.example.building.mvp.BasePresenter;
import com.example.building.mvp.contract.InfoContract;
import com.example.building.net.APIFunction;
import com.example.building.util.EnhancedCall;
import com.example.building.util.EnhancedCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class InfoPresenter extends BasePresenter implements InfoContract.InfoPresenter {

    InfoContract.InfoView infoView;

    public InfoPresenter(InfoContract.InfoView infoView) {
        this.infoView = infoView;
    }


    @Override
    public void getBusInfos() {
//        RetrofitFactory.getInstence().API().getBusInfo(LocalSaveData.getInstance().getUserName())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<List<TrafficInfoBean>>() {
//
//                    @Override
//                    protected void onSuccees(BaseEntity<List<TrafficInfoBean>> t) {
//                        infoView.showBusInfos(t.getResult());
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e) {
//
//                    }
//
//                    @Override
//                    protected void onFinish() {
//
//                    }
//                });
        APIFunction service = getApiService();
        Call<BaseEntity<List<TrafficInfoBean>>> call = service.getBusInfo(LocalSaveData.getInstance().getUserName());
        EnhancedCall<BaseEntity<List<TrafficInfoBean>>> enhancedCall = new EnhancedCall<>(call);
        enhancedCall.dataClassName(BaseEntity.class)
                .enqueue(new EnhancedCallback<BaseEntity<List<TrafficInfoBean>>>() {
                    @Override
                    public void onResponse(Call<BaseEntity<List<TrafficInfoBean>>> call, Response<BaseEntity<List<TrafficInfoBean>>> response) {
                        BaseEntity<List<TrafficInfoBean>> entity = response.body();
                        if (isSuccess(entity)) {
                            infoView.showBusInfos(entity.getResult());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseEntity<List<TrafficInfoBean>>> call, Throwable t) {
                        infoView.showLoadError();
                    }

                    @Override
                    public void onGetCache(BaseEntity<List<TrafficInfoBean>> entity) {
                        List<TrafficInfoBean> list = JSON.parseArray(entity.getResult() + "", TrafficInfoBean.class);
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        infoView.showBusInfos(list);
                    }
                });
    }

    @Override
    public void getInfos() {
//        RetrofitFactory.getInstence().API().getInfo(LocalSaveData.getInstance().getUserName())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<List<UnitInfoBean>>() {
//
//                    @Override
//                    protected void onSuccees(BaseEntity<List<UnitInfoBean>> t) {
//                        infoView.showInfos(t.getResult());
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e) {
//
//                    }
//
//                    @Override
//                    protected void onFinish() {
//                    }
//                });
        APIFunction service = getApiService();
        Call<BaseEntity<List<UnitInfoBean>>> call = service.getInfo(LocalSaveData.getInstance().getUserName());
        EnhancedCall<BaseEntity<List<UnitInfoBean>>> enhancedCall = new EnhancedCall<>(call);
        enhancedCall.dataClassName(BaseEntity.class)
                .enqueue(new EnhancedCallback<BaseEntity<List<UnitInfoBean>>>() {
                    @Override
                    public void onResponse(Call<BaseEntity<List<UnitInfoBean>>> call, Response<BaseEntity<List<UnitInfoBean>>> response) {
                        BaseEntity<List<UnitInfoBean>> entity = response.body();
                        if (isSuccess(entity)) {
                            infoView.showInfos(entity.getResult());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseEntity<List<UnitInfoBean>>> call, Throwable t) {
                        infoView.showLoadError();
                    }

                    @Override
                    public void onGetCache(BaseEntity<List<UnitInfoBean>> entity) {
                        List<UnitInfoBean> list = JSON.parseArray(entity.getResult() + "", UnitInfoBean.class);
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        infoView.showInfos(list);
                    }
                });
    }

    @Override
    public void getTels() {
//        RetrofitFactory.getInstence().API().getTel(LocalSaveData.getInstance().getUserName())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<List<AroundInfoBean>>() {
//
//                    @Override
//                    protected void onSuccees(BaseEntity<List<AroundInfoBean>> t) {
//                        infoView.showTels(t.getResult());
//                    }
//
//                    @Override
//                    protected void onFailure(Throwable e) {
//
//                    }
//
//                    @Override
//                    protected void onFinish() {
//                    }
//                });
        APIFunction service = getApiService();
        Call<BaseEntity<List<AroundInfoBean>>> call = service.getTel(LocalSaveData.getInstance().getUserName());
        EnhancedCall<BaseEntity<List<AroundInfoBean>>> enhancedCall = new EnhancedCall<>(call);
        enhancedCall.dataClassName(BaseEntity.class)
                .enqueue(new EnhancedCallback<BaseEntity<List<AroundInfoBean>>>() {
                    @Override
                    public void onResponse(Call<BaseEntity<List<AroundInfoBean>>> call, Response<BaseEntity<List<AroundInfoBean>>> response) {
                        BaseEntity<List<AroundInfoBean>> entity = response.body();
                        if (isSuccess(entity)) {
                            infoView.showTels(entity.getResult());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseEntity<List<AroundInfoBean>>> call, Throwable t) {
                        infoView.showLoadError();
                    }

                    @Override
                    public void onGetCache(BaseEntity<List<AroundInfoBean>> entity) {
                        List<AroundInfoBean> list = JSON.parseArray(entity.getResult() + "", AroundInfoBean.class);
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        infoView.showTels(list);
                    }
                });
    }
}
