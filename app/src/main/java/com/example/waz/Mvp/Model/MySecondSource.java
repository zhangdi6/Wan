package com.example.waz.Mvp.Model;

import com.example.waz.Api.ApiServer;
import com.example.waz.Base.BaseCallBack;
import com.example.waz.Mvp.Contract.SecondConstrat;
import com.example.waz.UI.Bean.SecondBean;
import com.example.waz.UI.Bean.WanBannerBean;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.waz.Base.BaseModel.observer;

public class MySecondSource implements SecondConstrat.SecondSource {

    @Override
    public void getSecond(RxFragment activity, BaseCallBack<SecondBean> callBack) {
        Retrofit build = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiServer.mWanUrl)
                .build();
        ApiServer apiServer = build.create(ApiServer.class);
        observer(activity, apiServer.getSecond(), new Function<SecondBean, ObservableSource<SecondBean>>() {
            @Override
            public ObservableSource<SecondBean> apply(SecondBean wanBannerBean) throws Exception {
                if (wanBannerBean != null) {
                    return Observable.just(wanBannerBean);
                }
                return Observable.error(new NullPointerException
                        ("返回数据为空或异常:" + wanBannerBean.getData() == null ? "" : wanBannerBean.getErrorMsg()));
            }

        }, callBack);
    }
}
