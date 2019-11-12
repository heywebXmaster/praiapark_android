package com.example.building.net;

import com.blankj.utilcode.util.StringUtils;
import com.example.building.config.HttpConfig;
import com.example.building.config.LocalSaveData;
import com.example.building.util.LogUtil;
import com.example.building.util.MD5Tool;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class DecodeRequestIntercepter implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //拿到所有Query的Key

        if ("POST".equals(request.method())) {
            LogUtil.i(request.body().toString());
            if (request.body() instanceof FormBody) {
                FormBody.Builder bodyBuilder = new FormBody.Builder();
                FormBody formBody = (FormBody) request.body();
                StringBuffer buffer = new StringBuffer();
                Map<String, Object> map = new HashMap<>();
                map.put("appId", HttpConfig.APP_ID);
                map.put("lang", LocalSaveData.getInstance().getLang());

                // 先复制原来的参数
                for (int i = 0; i < formBody.size(); i++) {
                    if(!StringUtils.isEmpty(formBody.encodedValue(i))){
                        bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                        map.put(formBody.encodedName(i), formBody.encodedValue(i));
                    }
                }
                Set set = map.keySet();
                Object[] arr = set.toArray();
                Arrays.sort(arr);
                for (Object key : arr) {
                    buffer.append(key)
                            .append("=")
                            .append(map.get(key))
                            .append("&");
                }
                buffer.append("appSecret")
                        .append("=")
                        .append(HttpConfig.APP_SECRET);
                LogUtil.e(buffer.toString());
                String firstMd5 = MD5Tool.md5(buffer.toString());
                String upperCase = firstMd5.toUpperCase();
                String finalMd5 = MD5Tool.md5(upperCase).toUpperCase();
                LogUtil.e(firstMd5);
                LogUtil.e(upperCase);
                LogUtil.e(finalMd5);
                // 添加公共参数
                formBody = bodyBuilder
                        .addEncoded("appId", HttpConfig.APP_ID)
                        .addEncoded("sign", finalMd5)
                        .addEncoded("lang", LocalSaveData.getInstance().getLang())
                        .build();
                request = request.newBuilder().post(formBody).build();
            }
        }
        return chain.proceed(request);
    }
}
