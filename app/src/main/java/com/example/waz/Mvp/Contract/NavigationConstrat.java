package com.example.waz.Mvp.Contract;

import com.example.waz.Base.BaseCallBack;
import com.example.waz.Base.BasePresenter;
import com.example.waz.Base.BaseView;
import com.example.waz.UI.Bean.NavigationBean;
import com.example.waz.UI.Bean.SecondBean;
import com.trello.rxlifecycle2.components.support.RxFragment;

public interface NavigationConstrat {

    public interface NavigationView extends BaseView<NavigationPresenter> {

        void onSuccess(NavigationBean datas);
        void onFail(String msg);

    }

    public interface NavigationPresenter extends BasePresenter<NavigationView> {

        void setNavigation();
    }

    public interface NavigationSource{
        void getNavigation(RxFragment activity, BaseCallBack<NavigationBean> callBack);
    }

}
