package com.example.waz.Mvp.Presenter;

import com.example.waz.Base.BaseCallBack;
import com.example.waz.Mvp.Contract.NavigationConstrat;
import com.example.waz.Mvp.Contract.SecondConstrat;
import com.example.waz.Mvp.Model.MyNavigationSource;
import com.example.waz.Mvp.Model.MySecondSource;
import com.example.waz.UI.Bean.NavigationBean;
import com.example.waz.UI.Bean.SecondBean;
import com.trello.rxlifecycle2.components.support.RxFragment;

public class MyNavigationPresenter implements NavigationConstrat.NavigationPresenter {
    private final NavigationConstrat.NavigationSource source;
    private NavigationConstrat.NavigationView views;

    public MyNavigationPresenter() {
        source = new MyNavigationSource();
    }

    @Override
    public void setNavigation() {
        if (source != null) {
            source.getNavigation((RxFragment) views, new BaseCallBack<NavigationBean>() {
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
    public void attachView(NavigationConstrat.NavigationView view) {
        views = view;
    }

    @Override
    public void detachView(NavigationConstrat.NavigationView view) {
        views = null;
    }
}
