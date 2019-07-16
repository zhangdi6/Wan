package com.example.waz.interceptor;
import android.text.TextUtils;

import com.example.waz.Api.App.MyApp;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class SaveCookieInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String domain = request.url().host();
        String requestUrl = request.url().toString();

        if (!TextUtils.isEmpty(response.header(AppConstant.SET_COOKIE_KEY))
                &&(requestUrl.contains(AppConstant.SAVE_USER_LOGIN_KEY)
                || requestUrl.contains(AppConstant.SAVE_USER_REGISTER_KEY))){
            //获取全部的Cookie
            List<String> cookies = response.headers(AppConstant.SET_COOKIE_KEY);

            if (cookies.size()>0){
                HashSet<String> cookieSet = new HashSet<>();
                for (String cookie : cookies) {
                    for (String value : cookie.split(";")) {
                        if (!TextUtils.isEmpty(value)){
                            cookieSet.add(value);
                        }
                    }
                }
            StringBuilder sb = new StringBuilder();
                for (String cookiesValue : cookieSet) {
                    sb.append(cookiesValue);
                    sb.append(";");
                }
                sb.deleteCharAt(sb.length()-1);
                SharePreferenceUtils.put(MyApp.mContext,"user",domain,sb.toString());
            }
        }
        return response;
    }
}
