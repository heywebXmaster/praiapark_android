package com.savills.praiapark.widget;

import android.view.View;
import com.savills.praiapark.R;
import me.shaohui.bottomdialog.BottomDialog;

public class TimePickerDialog extends BottomDialog {

    @Override
    public int getLayoutRes() {
        return R.layout.layout_time_picker_dialog;
    }

    @Override
    public void bindView(View v) {
        setHeight(800);
    }
}
