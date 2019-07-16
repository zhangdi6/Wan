package com.example.waz.Mvp.Presenter;

import com.example.waz.Base.BaseCallBack;
import com.example.waz.Mvp.Contract.WanConstrat;
import com.example.waz.Mvp.Model.MyWanSource;
import com.example.waz.UI.Bean.WanArticleBean;
import com.example.waz.UI.Bean.WanBannerBean;
import com.trello.rxlifecycle2.components.support.RxFragment;


public class MyWanPresenter implements WanConstrat.WanPresenter {
    private final WanConstrat.WanSource source;
    private WanConstrat.WanView views;

    public MyWanPresenter() {
        source = new MyWanSource();
    }

    @Override
    public void setBanner() {
        if (source != null) {
            source.getBanner((RxFragment) views, new BaseCallBack<WanBannerBean>() {
                @Override
                public void onSuccese(WanBannerBean data) {
                    if (views != null) {
                        views.onSuccessBanner(data);
                    }
                }

                @Override
                public void onFailed(String res) {
                    if (views != null) {
                        views.onFailBanner(res);
                    }
                }
            });
        }
    }

    @Override
    public void setArticle(int page) {
        source.getArticle(page,(RxFragment) views, new BaseCallBack<WanArticleBean>() {
            @Override
            public void onSuccese(WanArticleBean data) {
                if (views != null) {
                    views.onSuccessArticle(data);
                }
            }

            @Override
            public void onFailed(String res) {
                if (views != null) {
                    views.onFail(res);
                }
            }
        });
    }

    @Override
    public void attachView(WanConstrat.WanView view) {
        views = view;
    }

    @Override
    public void detachView(WanConstrat.WanView view) {
        views = null;
    }
}
