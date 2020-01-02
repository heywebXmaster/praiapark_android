package com.savills.praiapark.mvp.contract;

import com.savills.praiapark.bean.BookingBean;
import com.savills.praiapark.bean.DevicesBean;
import com.savills.praiapark.mvp.BaseView;

import java.util.List;

public interface BookingContract {

    interface BookingPresenter {
        void getDevicesList();

        void checkBookingTime(int facilityId,
                              String date,
                              int fromTime,
                              int toTime);

        void uploadBooking(String nickname,
                           String phoneNumber,
                           int facilityId,
                           String date,
                           int fromTime,
                           int toTime,
                           String amount);

        void getBookingByDate(int facilityId,String date);

    }

    interface OrderView extends BaseView {
        void showDevicesList(List<DevicesBean> list);

        void uploadBookingSuccess();

        void showBookingList(List<BookingBean> list);
    }
}
