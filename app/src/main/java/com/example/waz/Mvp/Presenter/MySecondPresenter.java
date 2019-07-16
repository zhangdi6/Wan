package com.example.waz.Mvp.Presenter;

import com.example.waz.Base.BaseCallBack;
import com.example.waz.Mvp.Contract.SecondConstrat;
import com.example.waz.Mvp.Contract.WanConstrat;
import com.example.waz.Mvp.Model.MySecondSource;
import com.example.waz.Mvp.Model.MyWanSource;
import com.example.waz.UI.Bean.SecondBean;
import com.example.waz.UI.Bean.WanArticleBean;
import com.example.waz.UI.Bean.WanBannerBean;
import com.trello.rxlifecycle2.components.support.RxFragment;

public class MySecondPresenter implements SecondConstrat.SecondPresenter {
    private final SecondConstrat.SecondSource source;
    private SecondConstrat.SecondView views;

    public MySecondPresenter() {
        source = new MySecondSource();
    }

    @Override
    public void setSecond() {
        if (source != null) {
            source.getSecond((RxFragment) views, new BaseCallBack<SecondBean>() {
                @Override
                public void onSuccese(SecondBean data) {
                    if (views != null) {
                        views.onSuccess(data);
                    }
                }

                @Override
                public void onFailed(String res) {
                    if (views != null) {
                        views.onFailSecond(res);
                    }
                }
            });
        }
    }


    @Override
    public void attachView(SecondConstrat.SecondView view) {
        views = view;
    }

    @Override
    public void detachView(SecondConstrat.SecondView view) {
        views = null;
    }
}
