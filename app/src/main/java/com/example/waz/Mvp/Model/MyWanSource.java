package com.example.waz.Mvp.Model;

import com.example.waz.Api.ApiServer;
import com.example.waz.Base.BaseCallBack;
import com.example.waz.Base.BaseModel;
import com.example.waz.Mvp.Contract.WanConstrat;
import com.example.waz.UI.Bean.WanArticleBean;
import com.example.waz.UI.Bean.WanBannerBean;
import com.trello.rxlifecycle2.components.support.RxFragment;



import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class MyWanSource extends BaseModel implements WanConstrat.WanSource {
    @Override
    public void getBanner(RxFragment activity, BaseCallBack<WanBannerBean> callBack) {
        Retrofit build = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiServer.mWanUrl)
                .build();
        ApiServer apiServer = build.create(ApiServer.class);
        observer(activity, apiServer.getBanner(), new Function<WanBannerBean, ObservableSource<WanBannerBean>>() {
            @Override
            public ObservableSource<WanBannerBean> apply(WanBannerBean wanBannerBean) throws Exception {
                if (wanBannerBean != null) {
                    return Observable.just(wanBannerBean);
                }
                return Observable.error(new NullPointerException
                        ("返回数据为空或异常:" + wanBannerBean.getData() == null ? "" : wanBannerBean.getErrorMsg()));
            }

        }, callBack);
    }

    @Override
    public void getArticle(int page,RxFragment activity, BaseCallBack<WanArticleBean> callBack) {
        Retrofit build = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiServer.mWanUrl)
                .build();
        ApiServer apiServer = build.create(ApiServer.class);
        observer(activity, apiServer.getWanArticle(page), new Function<WanArticleBean, ObservableSource<WanArticleBean>>() {
            @Override
            public ObservableSource<WanArticleBean> apply(WanArticleBean wanArticleBean) throws Exception {
               if (wanArticleBean!=null){
                   return Observable.just(wanArticleBean);
               }
                return  Observable.error(new NullPointerException
                        ("返回数据为空或异常:" + wanArticleBean.getData() == null ? "" : wanArticleBean.getErrorMsg()));
            }
        },callBack);
    }



}
