package com.savills.praiapark.mvp.contract;

import com.savills.praiapark.bean.AnnouncementBean;
import com.savills.praiapark.bean.NoticeBean;
import com.savills.praiapark.mvp.BaseView;

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
