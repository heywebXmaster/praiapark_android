package com.savills.praiapark.ui.main.club;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.Log;
import android.view.View;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
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
import com.savills.praiapark.ui.main.ClubServiceFragment;
import com.savills.praiapark.ui.main.MainFragment;
import com.savills.praiapark.ui.user.UserProfileFragment;
import com.savills.praiapark.util.LogUtil;
import com.savills.praiapark.util.ToastUtil;
import com.savills.praiapark.widget.TimePickDialog;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookingCalendarFragment extends BaseFragment<FragmentOrderCalendarBinding> implements ClickPresenter, BookingContract.OrderView, OnDateSelectedListener {

    private BookingPresenter bookingPresenter;
    public static final String DEVICES_INFO = "devices_info";
    private DevicesBean devicesBean;
    private BookingAdapter bookingAdapter;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private TimePickDialog dialog;
    private String selectDate;
    private static final int REQUEST_CODE = 001;

    public static BookingCalendarFragment newInstant(DevicesBean devicesBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DEVICES_INFO, devicesBean);
        BookingCalendarFragment bookingCalendarFragment = new BookingCalendarFragment();
        bookingCalendarFragment.setArguments(bundle);
        return bookingCalendarFragment;
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_order_calendar;
    }

    @Override
    protected void setTitle() {
        dataBinding.layoutHeader.setTitle(devicesBean.getName());
    }

    @Override
    protected void initView() {
        bookingPresenter = new BookingPresenter(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            devicesBean = (DevicesBean) bundle.getSerializable(DEVICES_INFO);
            dataBinding.setDevicesName(devicesBean.getName());
            if(devicesBean.getIconId()!=0){
                dataBinding.ivIcon.setImageResource(devicesBean.getIconId());
            }
        }
        bookingAdapter = new BookingAdapter();
        dataBinding.recyclerView.setAdapter(bookingAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("CheckResult")
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        DateFormatTitleFormatter dateFormatTitleFormatter = new DateFormatTitleFormatter(DateTimeFormatter.ofPattern("yyyy/MM"));
        dataBinding.layoutCalendar.calendarView
                .setTitleFormatter(dateFormatTitleFormatter);
        dataBinding.layoutCalendar.calendarView.setPagingEnabled(false);
        dataBinding.layoutCalendar.calendarView.addDecorators(
                new EnableOneToTenDecorator()
        );
        dataBinding.layoutCalendar.calendarView.setOnDateChangedListener(this);
        LocalDate today = LocalDate.now();
//        LocalDate tomorrow = today.plusDays(1);
        dataBinding.layoutCalendar.calendarView.setSelectedDate(today);

//        Date dt = new Date();
//        Calendar c = Calendar.getInstance();
//        c.setTime(dt);
//        c.add(Calendar.DATE, 1);
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH)+1;
//        int day = c.get(Calendar.DAY_OF_MONTH);
//
//        Log.e("xxx","year="+year);
//        Log.e("xxx","month="+month);
//        Log.e("xxx","day="+day);
//        dataBinding.layoutCalendar.calendarView.state().edit().setMinimumDate(CalendarDay.from(year, month, day)).commit();

//        bookingPresenter.getClubhouseNote();
        getBookingList();
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        if (selected) {
            selectDate = FORMATTER.format(date.getDate());
            bookingAdapter.clearDatas();
            bookingPresenter.getBookingByDate(devicesBean.getId(), selectDate);
        }
    }

    private void getBookingList() {
        CalendarDay calendarDay = dataBinding.layoutCalendar.calendarView.getSelectedDate();
        selectDate = FORMATTER.format(calendarDay.getDate());
        bookingPresenter.getBookingByDate(devicesBean.getId(), selectDate);
    }

    @Override
    protected void setListener() {
        dataBinding.layoutCalendar.setPresenter(this);
        dataBinding.layoutHeader.setPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                pop();
                break;
            case R.id.tvSelectTime:
                dialog = new TimePickDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable(DEVICES_INFO, devicesBean);
                dialog.setArguments(bundle);
                dialog.setClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bookingPresenter.checkBookingTime(devicesBean.getId(), selectDate, dialog.getFromTime(), dialog.getToTime());
                    }
                });
                dialog.show(getFragmentManager(), "pick");
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
        if (list.size() == 0) {
            dataBinding.layoutBottom.setVisibility(View.GONE);
        } else {
            dataBinding.layoutBottom.setVisibility(View.VISIBLE);
            dataBinding.recyclerView.setNestedScrollingEnabled(false);
            bookingAdapter.setDataList(list);
            bookingAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void checkBookingTimeSuccess(DevicesBean devicesBean) {
        dialog.dismiss();
        devicesBean.setKey(this.devicesBean.getKey());
        String title = dataBinding.layoutHeader.getTitle();
        startForResult(AddOrderFragment.newInstant(devicesBean, selectDate, dialog.getFromTime(), dialog.getToTime(),title), REQUEST_CODE);
    }

    @Override
    public void showClubhouseNote(String notes) {
        dataBinding.layoutCalendar.setNotes(getString(R.string.text_add_order_remind) + notes);
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

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            bookingPresenter.getBookingByDate(devicesBean.getId(), selectDate);
        }
    }
}
