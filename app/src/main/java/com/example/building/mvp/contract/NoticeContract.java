package com.example.building.mvp.contract;

import com.example.building.bean.AnnouncementBean;
import com.example.building.bean.NoticeBean;
import com.example.building.mvp.BaseView;

import java.util.List;

public interface NoticeContract {
    interface NoticePresneter {
        void getMessages(int page);

        void getAnnouncements(int page);
    }

    interface NoticeView extends BaseView {
        void showMessages(List<NoticeBean.NoticeItemBean> list);

        void showAnnouncements(List<AnnouncementBean.AnnouncementItemBean> list);
    }
}
