package com.savills.praiapark.mvp.presenter;

import com.alibaba.fastjson.JSON;
import com.savills.praiapark.bean.BaseEntity;
import com.savills.praiapark.bean.PdfBean;
import com.savills.praiapark.bean. UnitInfoBean;
import com.savills.praiapark.bean. UnitInfoBean;
import com.savills.praiapark.bean.DevicesBean;
import com.savills.praiapark.config.LocalSaveData;
import com.savills.praiapark.mvp.BasePresenter;
import com.savills.praiapark.mvp.contract.ClubInfoContract;
import com.savills.praiapark.net.APIFunction;
import com.savills.praiapark.net.BaseObserver;
import com.savills.praiapark.net.RetrofitFactory;
import com.savills.praiapark.util.EnhancedCall;
import com.savills.praiapark.util.EnhancedCallback;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class ClubInfoPresenter extends BasePresenter implements ClubInfoContract.ClubInfoPresenter {

    ClubInfoContract.ClubInfoView clubInfoView;

    public ClubInfoPresenter(ClubInfoContract.ClubInfoView clubInfoView){
        this.clubInfoView=clubInfoView;
    }

    @Override
    public void getDevicesList() {
        APIFunction service = getApiService();
        Call<BaseEntity<List<DevicesBean>>> call = service.getDevicesList(LocalSaveData.getInstance().getUserName());
        EnhancedCall<BaseEntity<List<DevicesBean>>> enhancedCall = new EnhancedCall<>(call);
        enhancedCall.dataClassName(BaseEntity.class)
                .enqueue(new EnhancedCallback<BaseEntity<List<DevicesBean>>>() {
                    @Override
                    public void onResponse(Call<BaseEntity<List<DevicesBean>>> call, Response<BaseEntity<List<DevicesBean>>> response) {
                        BaseEntity<List<DevicesBean>> entity = response.body();
                        if (isSuccess(entity)) {
                            clubInfoView.showDevicesList(entity.getResult());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseEntity<List<DevicesBean>>> call, Throwable t) {
                        clubInfoView.showLoadError();
                    }

                    @Override
                    public void onGetCache(BaseEntity<List<DevicesBean>> listBaseEntity) {
                        List<DevicesBean> list = JSON.parseArray(listBaseEntity.getResult() + "", DevicesBean.class);
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        clubInfoView.showDevicesList(list);
                    }
                });
    }

    @Override
    public void getClubRuleList() {
        APIFunction service = getApiService();
        Call<BaseEntity<List< UnitInfoBean>>> call = service.getClubRuleList(LocalSaveData.getInstance().getUserName());
        EnhancedCall<BaseEntity<List< UnitInfoBean>>> enhancedCall = new EnhancedCall<>(call);
        enhancedCall.dataClassName(BaseEntity.class)
                .enqueue(new EnhancedCallback<BaseEntity<List< UnitInfoBean>>>() {
                    @Override
                    public void onResponse(Call<BaseEntity<List< UnitInfoBean>>> call, Response<BaseEntity<List< UnitInfoBean>>> response) {
                        BaseEntity<List< UnitInfoBean>> entity = response.body();
                        if (isSuccess(entity)) {
                            clubInfoView.showClubRuleList(entity.getResult());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseEntity<List< UnitInfoBean>>> call, Throwable t) {
                        clubInfoView.showLoadError();
                    }

                    @Override
                    public void onGetCache(BaseEntity<List< UnitInfoBean>> listBaseEntity) {
                        List< UnitInfoBean> list = JSON.parseArray(listBaseEntity.getResult() + "",  UnitInfoBean.class);
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        clubInfoView.showClubRuleList(list);
                    }
                });
    }

    @Override
    public void getClubPrice() {
        clubInfoView.showLoading();
        RetrofitFactory.getInstence().API().getClubPrice(LocalSaveData.getInstance().getUserName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<PdfBean>() {
                    @Override
                    protected void onSuccees(BaseEntity<PdfBean> t) {
                        clubInfoView.showClubPrice(t.getResult());
                    }

                    @Override
                    protected void onFailure(Throwable e) {

                    }

                    @Override
                    protected void onFinish() {
                        clubInfoView.hideLoading();
                    }
                });
    }
}
