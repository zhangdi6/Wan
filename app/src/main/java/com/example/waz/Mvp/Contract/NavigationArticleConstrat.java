package com.example.waz.Mvp.Contract;

import com.example.waz.Base.BaseCallBack;
import com.example.waz.Base.BasePresenter;
import com.example.waz.Base.BaseView;
import com.example.waz.UI.Bean.NavigationBean;
import com.trello.rxlifecycle2.components.support.RxFragment;

public interface NavigationArticleConstrat {

    public interface NavigationArticleView extends BaseView<NavigationArticlePresenter> {

        void onSuccess(NavigationBean datas);
        void onFail(String msg);

    }

    public interface NavigationArticlePresenter extends BasePresenter<NavigationArticleView> {

        void setNavigationArticle();
    }

    public interface NavigationArticleSource{
        void getNavigationArticle(RxFragment activity, BaseCallBack<NavigationBean> callBack);
    }

}
