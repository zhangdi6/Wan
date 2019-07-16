package com.example.waz.Mvp.Presenter;

import com.example.waz.Base.BaseCallBack;
import com.example.waz.Mvp.Contract.NavigationArticleConstrat;
import com.example.waz.Mvp.Contract.NavigationConstrat;
import com.example.waz.Mvp.Model.MyNavigationArticleSource;
import com.example.waz.Mvp.Model.MyNavigationSource;
import com.example.waz.UI.Bean.NavigationBean;
import com.trello.rxlifecycle2.components.support.RxFragment;

public class MyNavigationArticlePresenter
        implements NavigationArticleConstrat.NavigationArticlePresenter {
    private final NavigationArticleConstrat.NavigationArticleSource source;
    private NavigationArticleConstrat.NavigationArticleView views;

    public MyNavigationArticlePresenter() {
        source = new MyNavigationArticleSource();
    }

    @Override
    public void setNavigationArticle() {
        if (source != null) {
            source.getNavigationArticle((RxFragment) views, new BaseCallBack<NavigationBean>() {
                @Override
                public void onSuccese(NavigationBean data) {
                    if (views != null) {
                        views.onSuccess(data);
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
    }


    @Override
    public void attachView(NavigationArticleConstrat.NavigationArticleView view) {
        views = view;
    }

    @Override
    public void detachView(NavigationArticleConstrat.NavigationArticleView view) {
        views = null;
    }
}
