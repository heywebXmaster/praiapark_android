package com.savills.praiapark.net;

import com.blankj.utilcode.util.NetworkUtils;
import com.savills.praiapark.base.App;
import com.savills.praiapark.config.HttpConfig;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;


public class RetrofitFactory {

    private static RetrofitFactory mRetrofitFactory;
    private static APIFunction mAPIFunction;
    private static final int HTTP_TIME = 3000;

    private RetrofitFactory() {
        File cacheFile = new File(App.getInstance().getCacheDir(), "responses");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 20);
        OkHttpClient mOkHttpClient = RetrofitUrlManager.getInstance().with(new OkHttpClient.Builder())
                .connectTimeout(HTTP_TIME, TimeUnit.MILLISECONDS)
                .readTimeout(HTTP_TIME, TimeUnit.MILLISECONDS)
                .writeTimeout(HTTP_TIME, TimeUnit.MILLISECONDS)
                .addNetworkInterceptor(networkInterceptor)
                .addInterceptor(new DecodeRequestIntercepter())
                .addInterceptor(InterceptorUtil.LogInterceptor())//添加日志拦截器
                .addInterceptor(interceptor)
                .cache(cache)
                .build();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(HttpConfig.BASE_URL)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava转换器
                .client(mOkHttpClient)
                .build();
        mAPIFunction = mRetrofit.create(APIFunction.class);
    }


    private static Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtils.isConnected()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            return chain.proceed(request);
        }
    };


    private static Interceptor networkInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            if (NetworkUtils.isConnected()) {
                int maxAge = 60;//單位秒
                response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .removeHeader("Pragma")
                        .build();
            } else {
                // 无网络时，设置超时为1周
                int maxStale = 60 * 60 * 24;
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
            return response;
        }
    };

    public static RetrofitFactory getInstence() {
        if (mRetrofitFactory == null) {
            synchronized (RetrofitFactory.class) {
                if (mRetrofitFactory == null)
                    mRetrofitFactory = new RetrofitFactory();
            }

        }
        return mRetrofitFactory;
    }

    public APIFunction API() {
        return mAPIFunction;
    }
}
