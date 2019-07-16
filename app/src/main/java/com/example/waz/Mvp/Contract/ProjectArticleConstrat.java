package com.example.waz.Mvp.Contract;

import com.example.waz.Base.BaseCallBack;
import com.example.waz.Base.BasePresenter;
import com.example.waz.Base.BaseView;
import com.example.waz.UI.Bean.ProjectArticleBean;
import com.example.waz.UI.Bean.ProjectBean;
import com.trello.rxlifecycle2.components.support.RxFragment;

public interface ProjectArticleConstrat {

    public interface ProjectArticleView extends BaseView<ProjectArticlePresenter> {

        void onSuccess(ProjectArticleBean datas);
        void onFail(String msg);

    }
    public interface ProjectArticlePresenter extends BasePresenter<ProjectArticleView> {
        void setProjectArticle(int page,int cid);
    }

    public interface ProjectArticleSource{
        void getProjectArticle(int page,int cid,RxFragment activity, BaseCallBack<ProjectArticleBean> callBack);
    }

}
