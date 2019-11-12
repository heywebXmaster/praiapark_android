package com.example.building.mvp.contract;

import com.example.building.bean.AroundInfoBean;
import com.example.building.bean.TrafficInfoBean;
import com.example.building.bean.UnitInfoBean;
import com.example.building.mvp.BaseView;

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
