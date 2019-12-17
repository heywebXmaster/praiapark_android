package com.savills.praiapark.ui.main;

import android.view.View;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;
import com.savills.praiapark.R;
import com.savills.praiapark.adapter.OrderAdapter;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.base.ClickPresenter;
import com.savills.praiapark.bean.OrderBean;
import com.savills.praiapark.databinding.FragmentOrderCalendarBinding;
import com.savills.praiapark.widget.TimePickerDialog;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import me.shaohui.bottomdialog.BottomDialog;

public class OrderCalendarFragment extends BaseFragment<FragmentOrderCalendarBinding> implements ClickPresenter {

    public static OrderCalendarFragment newInstant() {
        return new OrderCalendarFragment();
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_order_calendar;
    }

    @Override
    protected void setTitle() {
        dataBinding.layoutHeader.setTitle("電影院x1");
    }

    @Override
    protected void initView() {
        List<OrderBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new OrderBean());
        }
        dataBinding.recyclerView.setNestedScrollingEnabled(false);
        DateFormatTitleFormatter dateFormatTitleFormatter = new DateFormatTitleFormatter(DateTimeFormatter.ofPattern("yyyy/MM"));
        dataBinding.layoutCalendar.calendarView
                .setTitleFormatter(dateFormatTitleFormatter);
        dataBinding.layoutCalendar.calendarView.setPagingEnabled(false);
        dataBinding.layoutCalendar.calendarView.addDecorators(
                new EnableOneToTenDecorator()
        );
        OrderAdapter orderAdapter = new OrderAdapter();
        dataBinding.recyclerView.setAdapter(orderAdapter);
        orderAdapter.setDataList(list);
        orderAdapter.notifyDataSetChanged();
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

                break;
            case R.id.tvSelectTime:
//                TimePickerDialog dialog = new TimePickerDialog();
//                dialog.show(getFragmentManager());
                break;
        }
    }

    private static class EnableOneToTenDecorator implements DayViewDecorator {

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            CalendarDay calendarDay = CalendarDay.today();
            boolean a = day.isBefore(calendarDay);
            return a;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setDaysDisabled(true);
        }
    }
}
