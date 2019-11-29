package com.savills.praiapark.mvp.contract;

import com.savills.praiapark.bean.PdfBean;
import com.savills.praiapark.mvp.BaseView;

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
