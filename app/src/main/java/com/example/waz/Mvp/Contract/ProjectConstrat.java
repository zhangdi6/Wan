package com.example.waz.Mvp.Contract;

import com.example.waz.Base.BaseCallBack;
import com.example.waz.Base.BasePresenter;
import com.example.waz.Base.BaseView;
import com.example.waz.UI.Bean.NavigationBean;
import com.example.waz.UI.Bean.ProjectBean;
import com.trello.rxlifecycle2.components.support.RxFragment;

public interface ProjectConstrat {

    public interface ProjectView extends BaseView<ProjectPresenter> {
        void onSuccess(ProjectBean datas);
        void onFail(String msg);

    }
    public interface ProjectPresenter extends BasePresenter<ProjectView> {

        void setProject();
    }

    public interface ProjectSource{
        void getProject(RxFragment activity, BaseCallBack<ProjectBean> callBack);
    }

}
