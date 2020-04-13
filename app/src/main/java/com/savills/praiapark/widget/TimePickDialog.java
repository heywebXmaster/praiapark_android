package com.savills.praiapark.widget;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.github.chrisbanes.photoview.PhotoView;
import com.savills.praiapark.R;
import com.savills.praiapark.bean.DevicesBean;
import com.savills.praiapark.ui.main.club.BookingCalendarFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimePickDialog extends DialogFragment {

    private WheelView wvLeft;
    private WheelView wvRight;
    private static final String[] PLANETS = new String[]{"11", "12", "13", "14", "15", "16", "17", "18"};
    private AppCompatTextView tvCancel;
    private AppCompatTextView tvConfirm;
    private DevicesBean devicesBean;
    private int fromTime = 0;
    private int toTime = 0;
    private View.OnClickListener cancel;
    private View.OnClickListener confirm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_dialog_picker, container);
        wvLeft = view.findViewById(R.id.wvLeft);
        wvRight = view.findViewById(R.id.wvRight);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvConfirm = view.findViewById(R.id.tvConfirm);
        Bundle bundle = getArguments();
        if (bundle != null) {
            devicesBean = (DevicesBean) bundle.getSerializable(BookingCalendarFragment.DEVICES_INFO);
        }
        initView();
        return view;
    }

    private void initView() {
        List<String> selectIndex = new ArrayList<>();
        for (int i = devicesBean.getFromTime(); i <= devicesBean.getToTime(); i++) {
            selectIndex.add(i + ":00");
        }
        fromTime=devicesBean.getFromTime();
        toTime=fromTime;
        wvLeft.setOffset(2);
        wvLeft.setItems(selectIndex);
        wvRight.setOffset(2);
        wvRight.setItems(selectIndex);
        wvLeft.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                String parseItem=item.split(":")[0];
                fromTime=Integer.parseInt(parseItem);
            }
        });
        wvRight.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                String parseItem=item.split(":")[0];
                toTime=Integer.parseInt(parseItem);
            }
        });
        tvCancel.setOnClickListener(cancel);
        tvConfirm.setOnClickListener(confirm);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
//        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void setClickListener(View.OnClickListener cancel, View.OnClickListener confirm){
        this.cancel=cancel;
        this.confirm=confirm;
    }

    public int getFromTime(){
        return fromTime;
    }

    public int getToTime(){
        return toTime;
    }
}
