package com.savills.praiapark.util;

import android.widget.Toast;

import com.savills.praiapark.base.ActivityManager;

public class ToastUtil {

    private static Toast toast;

    public static void show(CharSequence message) {
        if (toast == null) {
            toast = Toast.makeText(ActivityManager.getInstance().getCurrentActivity(),
                    message,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

}
