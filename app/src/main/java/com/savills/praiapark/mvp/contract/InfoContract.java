package com.savills.praiapark.mvp.contract;

import com.savills.praiapark.bean.AroundInfoBean;
import com.savills.praiapark.bean.TrafficInfoBean;
import com.savills.praiapark.bean.UnitInfoBean;
import com.savills.praiapark.mvp.BaseView;

import java.util.List;

public interface InfoContract {

    interface InfoPresenter {
        void getBusInfos();

        void getInfos();

        void getTels();
    }

    interface InfoView extends BaseView {
        void showBusInfos(List<TrafficInfoBean> list);

        void showInfos(List<UnitInfoBean> list);

        void showTels(List<AroundInfoBean> list);
    }
}
