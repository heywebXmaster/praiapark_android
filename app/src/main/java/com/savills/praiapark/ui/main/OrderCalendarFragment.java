package com.savills.praiapark.ui.main;

import android.view.View;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;
import com.savills.praiapark.R;
import com.savills.praiapark.adapter.AddOrderAdapter;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.base.ClickPresenter;
import com.savills.praiapark.bean.OrderBean;
import com.savills.praiapark.databinding.FragmentOrderCalendarBinding;
import org.threeten.bp.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
        AddOrderAdapter AddOrderAdapter = new AddOrderAdapter();
        dataBinding.recyclerView.setAdapter(AddOrderAdapter);
        AddOrderAdapter.setDataList(list);
        AddOrderAdapter.notifyDataSetChanged();
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
//                TimePickerDialog dialog = new TimePickerDialog();
//                dialog.show(getFragmentManager());
                break;
        }
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
