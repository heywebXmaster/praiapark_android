package com.savills.praiapark.mvp.contract;

import com.savills.praiapark.bean.ClubPriceBean;
import com.savills.praiapark.bean.ClubRuleBean;
import com.savills.praiapark.bean.DevicesBean;
import com.savills.praiapark.bean.UnitInfoBean;
import com.savills.praiapark.mvp.BaseView;

import java.util.List;

public interface ClubInfoContract {
    interface ClubInfoPresenter {
        void getDevicesList();

        void getClubRuleList();

        void getClubPriceList();

    }

    interface ClubInfoView extends BaseView {
        void showDevicesList(List<DevicesBean> list);

        void showClubRuleList(List<UnitInfoBean> list);

        void showClubPriceList(List<UnitInfoBean> list);
    }
}
