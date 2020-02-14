package com.savills.praiapark.mvp.contract;

import com.savills.praiapark.bean.ClubPriceBean;
import com.savills.praiapark.bean.ClubRuleBean;
import com.savills.praiapark.bean.DevicesBean;
import com.savills.praiapark.bean.PdfBean;
import com.savills.praiapark.bean.UnitInfoBean;
import com.savills.praiapark.mvp.BaseView;

import java.util.List;

public interface ClubInfoContract {
    interface ClubInfoPresenter {
        void getDevicesList();

        void getClubRuleList();

        void getClubPrice();

        void parseListIcon(List<DevicesBean> list);

    }

    interface ClubInfoView extends BaseView {
        void showDevicesList(List<DevicesBean> list);

        void showClubRuleList(List<UnitInfoBean> list);

        void showClubPrice(PdfBean pdfBean);

        void showParseIconList(List<DevicesBean> list);
    }
}
