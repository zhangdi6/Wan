package com.example.waz.Mvp.Contract;

import com.example.waz.Base.BaseCallBack;
import com.example.waz.Base.BasePresenter;
import com.example.waz.Base.BaseView;
import com.example.waz.UI.Bean.WanArticleBean;
import com.example.waz.UI.Bean.WanBannerBean;

import com.trello.rxlifecycle2.components.support.RxFragment;
import com.youth.banner.Banner;

import java.util.List;

public interface WanConstrat {

    public interface WanView extends BaseView<WanPresenter> {

        void onSuccessBanner(WanBannerBean datas);
        void onFailBanner(String msg);
        void onSuccessArticle(WanArticleBean datas);
        void onFail(String msg);
    }

    public interface WanPresenter extends BasePresenter<WanView> {

        void setBanner();
        void setArticle(int page);
    }

    public interface WanSource{
        void getBanner(RxFragment activity, BaseCallBack<WanBannerBean> callBack);
        void getArticle(int page,RxFragment activity, BaseCallBack<WanArticleBean>callBack);
    }

}
