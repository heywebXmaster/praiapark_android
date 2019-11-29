package com.savills.praiapark.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkUtil {

    /**
     * 网络是否可用
     *
     * @param context 上下文对象
     * @return 联网状态，true表示已连上
     */
    public static boolean isNetworkAvailable(Context context) {
         ConnectivityManager mgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
         if (mgr!=null) {
             NetworkInfo info = mgr.getActiveNetworkInfo();
             if (info != null) {
                 return info.isAvailable();
             }
         }
        return false;
    }

}
