package com.example.building.mvp.contract;

import com.example.building.bean.PdfBean;
import com.example.building.mvp.BaseView;

import java.util.List;

public interface SettingContract {
    interface SettingPresenter {
        void contactUs(String title, String content, String nickname, String phoneNumber);


        void getPdfs();
    }

    interface SettingView extends BaseView {
        void contactUsSuccess();



        void showPdfs(List<PdfBean> list);
    }
}
