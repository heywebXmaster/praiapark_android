package com.savills.praiapark.mvp;

import android.graphics.Color;

import com.blankj.utilcode.util.ToastUtils;
import com.savills.praiapark.bean.BaseEntity;
import com.savills.praiapark.config.HttpConfig;
import com.savills.praiapark.net.APIFunction;
import com.savills.praiapark.net.DecodeRequestIntercepter;
import com.savills.praiapark.net.InterceptorUtil;
import com.savills.praiapark.util.EnhancedCacheInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

public class BasePresenter {

    //构造缓存机制的service
    public APIFunction getApiService() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new EnhancedCacheInterceptor())
                .addInterceptor(new DecodeRequestIntercepter())
                .addInterceptor(InterceptorUtil.LogInterceptor());

        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpConfig.BASE_URL)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit.create(APIFunction.class);
    }

    public boolean isSuccess(BaseEntity entity) {
        ToastUtils.setBgColor(Color.RED);
        ToastUtils.setMsgColor(Color.WHITE);
        try{
            if (entity.isSuccess()) {
                return true;
            } else {
                ToastUtils.showShort(entity.getErrorMsg());
                return false;
            }
        }catch (Exception ex){
            ToastUtils.showShort(ex.toString());
            return false;
        }

    }

}
