package com.savills.praiapark.ui.main.club;

import android.view.View;

import com.savills.praiapark.R;
import com.savills.praiapark.aop.annotation.SingleClick;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.base.ClickPresenter;
import com.savills.praiapark.bean.BookingBean;
import com.savills.praiapark.bean.DevicesBean;
import com.savills.praiapark.databinding.FragmentAddOrderBinding;
import com.savills.praiapark.mvp.contract.BookingContract;
import com.savills.praiapark.mvp.presenter.BookingPresenter;

import java.util.List;

public class AddOrderFragment extends BaseFragment<FragmentAddOrderBinding> implements ClickPresenter, BookingContract.OrderView {

    private BookingPresenter bookingPresenter;

    public static AddOrderFragment newInstant() {
        return new AddOrderFragment();
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_add_order;
    }

    @Override
    protected void setTitle() {
        dataBinding.layoutHeader.setTitle(getString(R.string.title_add_order));
    }

    @Override
    protected void initView() {
        bookingPresenter = new BookingPresenter(this);
    }

    @Override
    protected void setListener() {
        dataBinding.layoutHeader.setPresenter(this);
        dataBinding.setPresenter(this);
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                pop();
                break;
//            case R.id.tvSelectDevices:
//
//                break;
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
}
