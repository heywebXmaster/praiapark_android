package com.savills.praiapark.mvp.presenter;

import com.alibaba.fastjson.JSON;
import com.savills.praiapark.bean.BaseEntity;
import com.savills.praiapark.bean.BookingBean;
import com.savills.praiapark.bean.DevicesBean;
import com.savills.praiapark.config.LocalSaveData;
import com.savills.praiapark.mvp.BasePresenter;
import com.savills.praiapark.mvp.contract.BookingContract;
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

public class BookingPresenter extends BasePresenter implements BookingContract.BookingPresenter {

    BookingContract.OrderView orderView;

    public BookingPresenter(BookingContract.OrderView orderView) {
        this.orderView = orderView;
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
                            orderView.showDevicesList(entity.getResult());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseEntity<List<DevicesBean>>> call, Throwable t) {
                        orderView.showLoadError();
                    }

                    @Override
                    public void onGetCache(BaseEntity<List<DevicesBean>> listBaseEntity) {
                        List<DevicesBean> list = JSON.parseArray(listBaseEntity.getResult() + "", DevicesBean.class);
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        orderView.showDevicesList(list);
                    }
                });
    }

    @Override
    public void checkBookingTime(int facilityId, String date, int fromTime, int toTime) {
        orderView.showLoading();
        RetrofitFactory.getInstence().API().checkBookingTime(LocalSaveData.getInstance().getUserName(),
                facilityId,date,fromTime,toTime)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onSuccees(BaseEntity<String> t) {
                        orderView.showToast(t.getErrorMsg());
                    }

                    @Override
                    protected void onFailure(Throwable e) {

                    }

                    @Override
                    protected void onFinish() {
                        orderView.hideLoading();
                    }
                });
    }

    @Override
    public void uploadBooking(String nickname,
                              String phoneNumber,
                              int facilityId,
                              String date,
                              int fromTime,
                              int toTime,
                              String amount) {
        orderView.showLoading();
        RetrofitFactory.getInstence().API().uploadBooking(
                LocalSaveData.getInstance().getUserName(),
                nickname,
                phoneNumber,
                facilityId,
                date,
                fromTime,
                toTime,
                amount)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>() {

                    @Override
                    protected void onSuccees(BaseEntity<String> t) {
                        orderView.showToast(t.getErrorMsg());
                        orderView.uploadBookingSuccess();
                    }

                    @Override
                    protected void onFailure(Throwable e) {

                    }

                    @Override
                    protected void onFinish() {
                        orderView.hideLoading();
                    }
                });

    }

    @Override
    public void getBookingByDate(int facilityId, String date) {
        APIFunction service = getApiService();
        Call<BaseEntity<List<BookingBean>>> call = service.getBookingByDate(LocalSaveData.getInstance().getUserName(),facilityId,date);
        EnhancedCall<BaseEntity<List<BookingBean>>> enhancedCall = new EnhancedCall<>(call);
        enhancedCall.dataClassName(BaseEntity.class)
                .enqueue(new EnhancedCallback<BaseEntity<List<BookingBean>>>() {
                    @Override
                    public void onResponse(Call<BaseEntity<List<BookingBean>>> call, Response<BaseEntity<List<BookingBean>>> response) {
                        BaseEntity<List<BookingBean>> entity = response.body();
                        if (isSuccess(entity)) {
                            orderView.showBookingList(entity.getResult());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseEntity<List<BookingBean>>> call, Throwable t) {
                        orderView.showLoadError();
                    }

                    @Override
                    public void onGetCache(BaseEntity<List<BookingBean>> listBaseEntity) {
                        List<BookingBean> list = JSON.parseArray(listBaseEntity.getResult() + "", BookingBean.class);
                        if (list == null) {
                            list = new ArrayList<>();
                        }
                        orderView.showBookingList(list);
                    }
                });
    }




}
