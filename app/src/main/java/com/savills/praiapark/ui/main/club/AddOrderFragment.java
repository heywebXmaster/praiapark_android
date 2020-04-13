package com.savills.praiapark.ui.main.club;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.savills.praiapark.R;
import com.savills.praiapark.aop.annotation.SingleClick;
import com.savills.praiapark.base.BaseFragment;
import com.savills.praiapark.base.ClickPresenter;
import com.savills.praiapark.bean.BookingBean;
import com.savills.praiapark.bean.DevicesBean;
import com.savills.praiapark.databinding.FragmentAddOrderBinding;
import com.savills.praiapark.mvp.contract.BookingContract;
import com.savills.praiapark.mvp.presenter.BookingPresenter;

import java.math.BigDecimal;
import java.util.List;

import static com.savills.praiapark.ui.main.club.BookingCalendarFragment.DEVICES_INFO;

public class AddOrderFragment extends BaseFragment<FragmentAddOrderBinding> implements ClickPresenter, BookingContract.OrderView, TextWatcher {

    public static final String FROM_TIME = "from_time";
    public static final String TO_TIME = "to_time";
    public static final String SELECT_DATE = "select_date";
    private DevicesBean devicesBean;
    private int fromTime = 0;
    private int toTime = 0;
    private String selectDate;
    private String nickName;
    private String mobile;
    private String amount;

    public static AddOrderFragment newInstant(DevicesBean devicesBean, String selectDate, int fromTime, int toTime) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DEVICES_INFO, devicesBean);
        bundle.putInt(FROM_TIME, fromTime);
        bundle.putInt(TO_TIME, toTime);
        bundle.putString(SELECT_DATE, selectDate);
        AddOrderFragment addOrderFragment = new AddOrderFragment();
        addOrderFragment.setArguments(bundle);
        return addOrderFragment;
    }

    private BookingPresenter bookingPresenter;

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
        dataBinding.layoutHeader.tvright.setEnabled(false);
        dataBinding.layoutHeader.tvright.setText(R.string.text_add_order_submit);
        dataBinding.layoutHeader.tvright.setVisibility(View.VISIBLE);
        bookingPresenter = new BookingPresenter(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            devicesBean = (DevicesBean) bundle.getSerializable(DEVICES_INFO);
            if (Double.parseDouble(devicesBean.getSurcharge()) > 0) {
                dataBinding.layoutSurcharge.setVisibility(View.VISIBLE);
                dataBinding.setSurchargeName(devicesBean.getSurchargeName());
                dataBinding.setSurCharge("MOP "+devicesBean.getSurcharge());
            }
            selectDate = bundle.getString(SELECT_DATE);
            fromTime = bundle.getInt(FROM_TIME);
            toTime = bundle.getInt(TO_TIME);
            dataBinding.setDevice(devicesBean);
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < devicesBean.getPrice().getPrices().size(); i++) {
                DevicesBean.Prices.PriceInfo info = devicesBean.getPrice().getPrices().get(i);
                if (i != 0) {
                    buffer.append("\n");
                }
                buffer.append(info.getFromTime() + ":00");
                buffer.append(" - ");
                buffer.append(info.getToTime() + ":00");
                buffer.append(" ");
                buffer.append(info.getPrice());
                buffer.append("/");
                buffer.append((devicesBean.getHour() > 1 ? devicesBean.getHour() + getString(R.string.text_add_order_hour) : getString(R.string.text_add_order_hour)));
            }
            dataBinding.setPrice(buffer.toString());
            dataBinding.setSelectDate(selectDate);
            dataBinding.setSelectTime(fromTime + ":00 ~ " + toTime + ":00");
            amount = devicesBean.getAmount();
            dataBinding.setAmount("MOP " + amount);
        }

    }

    public static double multiply(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    @Override
    protected void setListener() {
        dataBinding.layoutHeader.setPresenter(this);
        dataBinding.setPresenter(this);
        dataBinding.etName.addTextChangedListener(this);
        dataBinding.etMobile.addTextChangedListener(this);
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                pop();
                break;
            case R.id.tvright:
                bookingPresenter.uploadBooking(nickName,
                        mobile,
                        devicesBean.getFacilityId(),
                        selectDate,
                        fromTime,
                        toTime,
                        amount);
                break;
        }
    }

    @Override
    public void showDevicesList(List<DevicesBean> list) {

    }

    @Override
    public void uploadBookingSuccess() {
        setResult(RESULT_OK, null);
        pop();
    }

    @Override
    public void showBookingList(List<BookingBean> list) {

    }

    @Override
    public void checkBookingTimeSuccess(DevicesBean devicesBean) {

    }

    @Override
    public void showClubhouseNote(String note) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        nickName = dataBinding.etName.getText().toString().trim();
        mobile = dataBinding.etMobile.getText().toString().trim();
        if (!StringUtils.isEmpty(nickName) && !StringUtils.isEmpty(mobile)) {
            dataBinding.layoutHeader.tvright.setEnabled(true);
        } else {
            dataBinding.layoutHeader.tvright.setEnabled(false);
        }
    }
}
