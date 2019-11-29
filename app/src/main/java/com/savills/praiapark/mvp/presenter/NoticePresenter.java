package com.savills.praiapark.mvp.presenter;

import com.alibaba.fastjson.JSON;
import com.savills.praiapark.bean.AnnouncementBean;
import com.savills.praiapark.bean.BaseEntity;
import com.savills.praiapark.bean.NoticeBean;
import com.savills.praiapark.config.LocalSaveData;
import com.savills.praiapark.mvp.BasePresenter;
import com.savills.praiapark.mvp.contract.NoticeContract;
import com.savills.praiapark.net.APIFunction;
import com.savills.praiapark.util.EnhancedCall;
import com.savills.praiapark.util.EnhancedCallback;
import com.savills.praiapark.util.LogUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class NoticePresenter extends BasePresenter implements NoticeContract.NoticePresneter {

    NoticeContract.NoticeView noticeView;

    public NoticePresenter(NoticeContract.NoticeView noticeView) {
        this.noticeView = noticeView;
    }

    @Override
    public void getMessages(int page) {
        APIFunction service = getApiService();
        Call<BaseEntity<NoticeBean>> call = service.getMessages(LocalSaveData.getInstance().getUserName(),
                page, 10);
        EnhancedCall<BaseEntity<NoticeBean>> enhancedCall = new EnhancedCall<>(call);
        enhancedCall.dataClassName(BaseEntity.class)
                .enqueue(new EnhancedCallback<BaseEntity<NoticeBean>>() {
                    @Override
                    public void onResponse(Call<BaseEntity<NoticeBean>> call, Response<BaseEntity<NoticeBean>> response) {
                        BaseEntity<NoticeBean> entity = response.body();
                        if (isSuccess(entity) && entity.getResult() != null) {
                            noticeView.showMessages(entity.getResult().getList());
                        }
                        LogUtil.i("onResponse:" + entity.getResult());
                    }

                    @Override
                    public void onFailure(Call<BaseEntity<NoticeBean>> call, Throwable t) {
                        LogUtil.i("onFailure");
                        noticeView.showLoadError();
                    }

                    @Override
                    public void onGetCache(BaseEntity<NoticeBean> entity) {
                        LogUtil.e("onGetCache" + entity.toString());
                        NoticeBean noticeBean = JSON.parseObject(entity.getResult() + "", NoticeBean.class);
                        if (noticeBean.getList() != null) {
                            noticeView.showMessages(noticeBean.getList());
                        }else {
                            noticeView.showMessages(new ArrayList<>());
                        }
                    }

                });

    }

    @Override
    public void getAnnouncements(int page) {
        APIFunction service = getApiService();
        Call<BaseEntity<AnnouncementBean>> call = service.getAnnouncements(LocalSaveData.getInstance().getUserName(),
                page, 10);
        EnhancedCall<BaseEntity<AnnouncementBean>> enhancedCall = new EnhancedCall<>(call);
        enhancedCall.dataClassName(BaseEntity.class)
                .enqueue(new EnhancedCallback<BaseEntity<AnnouncementBean>>() {
                    @Override
                    public void onResponse(Call<BaseEntity<AnnouncementBean>> call, Response<BaseEntity<AnnouncementBean>> response) {
                        BaseEntity<AnnouncementBean> entity = response.body();
                        if (isSuccess(entity)) {
                            noticeView.showAnnouncements(entity.getResult().getList());
                        }
                        LogUtil.i("onResponse:" + entity.getResult());
                    }

                    @Override
                    public void onFailure(Call<BaseEntity<AnnouncementBean>> call, Throwable t) {
                        LogUtil.i("onFailure");
                        noticeView.showLoadError();
                    }

                    @Override
                    public void onGetCache(BaseEntity<AnnouncementBean> entity) {
                        LogUtil.e("onGetCache" + entity.toString());
                        LogUtil.e(entity.getResult() + "");
                        AnnouncementBean announcementBean = JSON.parseObject(entity.getResult() + "", AnnouncementBean.class);
                        if (announcementBean.getList() != null) {
                            noticeView.showAnnouncements(announcementBean.getList());
                        } else {
                            noticeView.showAnnouncements(new ArrayList<>());
                        }
                    }
                });
    }
}
