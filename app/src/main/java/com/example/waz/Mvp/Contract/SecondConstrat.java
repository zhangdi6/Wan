package com.example.waz.Mvp.Contract;

import com.example.waz.Base.BaseCallBack;
import com.example.waz.Base.BasePresenter;
import com.example.waz.Base.BaseView;
import com.example.waz.UI.Bean.SecondBean;
import com.example.waz.UI.Bean.WanArticleBean;
import com.example.waz.UI.Bean.WanBannerBean;
import com.trello.rxlifecycle2.components.support.RxFragment;

public interface SecondConstrat {

    public interface SecondView extends BaseView<SecondPresenter> {

        void onSuccess(SecondBean datas);
        void onFailSecond(String msg);

    }

    public interface SecondPresenter extends BasePresenter<SecondView> {

        void setSecond();
    }

    public interface SecondSource{
        void getSecond(RxFragment activity, BaseCallBack<SecondBean> callBack);
    }

}
