package com.example.waz.Mvp.Model;

import com.example.waz.Api.ApiServer;
import com.example.waz.Base.BaseCallBack;
import com.example.waz.Mvp.Contract.ProjectConstrat;
import com.example.waz.UI.Bean.NavigationBean;
import com.example.waz.UI.Bean.ProjectBean;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.waz.Base.BaseModel.observer;

public class MyProjectSource implements ProjectConstrat.ProjectSource {
    @Override
    public void getProject(RxFragment activity, BaseCallBack<ProjectBean> callBack) {
        Retrofit build = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiServer.mWanUrl)
                .build();
        ApiServer apiServer = build.create(ApiServer.class);
        observer(activity, apiServer.getProject(), new Function<ProjectBean, ObservableSource<ProjectBean>>() {
            @Override
            public ObservableSource<ProjectBean> apply(ProjectBean wanBannerBean) throws Exception {
                if (wanBannerBean != null) {
                    return Observable.just(wanBannerBean);
                }
                return Observable.error(new NullPointerException
                        ("返回数据为空或异常:" + wanBannerBean.getData() == null ? "" : wanBannerBean.getErrorMsg()));
            }

        }, callBack);
    }
}
