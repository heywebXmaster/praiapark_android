package com.savills.praiapark.ui.main.club;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;
import com.savills.praiapark.R;
import com.savills.praiapark.adapter.BookingAdapter;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.base.ClickPresenter;
import com.savills.praiapark.bean.BookingBean;
import com.savills.praiapark.bean.DevicesBean;
import com.savills.praiapark.databinding.FragmentOrderCalendarBinding;
import com.savills.praiapark.mvp.contract.BookingContract;
import com.savills.praiapark.mvp.presenter.BookingPresenter;
import com.savills.praiapark.widget.TimePickDialog;
import org.threeten.bp.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BookingCalendarFragment extends BaseFragment<FragmentOrderCalendarBinding> implements ClickPresenter, BookingContract.OrderView {

    private BookingPresenter bookingPresenter;

    public static BookingCalendarFragment newInstant() {
        return new BookingCalendarFragment();
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_order_calendar;
    }

    @Override
    protected void setTitle() {
//        dataBinding.layoutHeader.setTitle("電影院x1");
    }

    @Override
    protected void initView() {
        bookingPresenter = new BookingPresenter(this);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        List<BookingBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new BookingBean());
        }
        dataBinding.recyclerView.setNestedScrollingEnabled(false);
        DateFormatTitleFormatter dateFormatTitleFormatter = new DateFormatTitleFormatter(DateTimeFormatter.ofPattern("yyyy/MM"));
        dataBinding.layoutCalendar.calendarView
                .setTitleFormatter(dateFormatTitleFormatter);
        dataBinding.layoutCalendar.calendarView.setPagingEnabled(false);
        dataBinding.layoutCalendar.calendarView.addDecorators(
                new EnableOneToTenDecorator()
        );
        BookingAdapter BookingAdapter = new BookingAdapter();
        dataBinding.recyclerView.setAdapter(BookingAdapter);
        BookingAdapter.setDataList(list);
        BookingAdapter.notifyDataSetChanged();
    }

    @Override
    protected void setListener() {
        dataBinding.layoutCalendar.setPresenter(this);
//        dataBinding.layoutHeader.setPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSelectTime:
                TimePickDialog dialog=new TimePickDialog();
                dialog.show(getFragmentManager(),"pick");
                break;
        }
    }

    @Override
    public void showDevicesList(List<DevicesBean> list) {

    }

    @Override
    public void uploadBookingSuccess() {

    }

    @Override
    public void showBookingList(List<BookingBean> list) {

    }

    private static class EnableOneToTenDecorator implements DayViewDecorator {

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            CalendarDay calendarDay = CalendarDay.today();
            boolean isBofore = day.isBefore(calendarDay);
            return isBofore;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setDaysDisabled(true);
        }
    }
}
