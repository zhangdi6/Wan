package com.example.waz.interceptor;

import android.text.TextUtils;


import com.example.waz.Api.App.MyApp;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddHederInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String domain = request.url().host();
        String requestUrl = request.url().toString();

        Request.Builder builder = request.newBuilder();
        if (requestUrl.contains(AppConstant.COLLECTIONS_WEBSITE)
        || requestUrl.contains(AppConstant.ARTICLE_WEBSITE)
        || requestUrl.contains(AppConstant.TODO_WEBSITE)
        || requestUrl.contains(AppConstant.UNCOLLECTIONS_WEBSITE)){

            String cookies = (String) SharePreferenceUtils.get(MyApp.mContext, "user", domain, "");
                if (!TextUtils.isEmpty(cookies)){
                    //添加到头信息
                    builder.addHeader(AppConstant.COOKIE_NAME,cookies);
                }
        }

        return chain.proceed(builder.build());
    }
}
