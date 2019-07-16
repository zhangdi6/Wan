package com.example.waz.Mvp.Presenter;

import com.example.waz.Base.BaseCallBack;
import com.example.waz.Mvp.Contract.NavigationConstrat;
import com.example.waz.Mvp.Contract.ProjectConstrat;
import com.example.waz.Mvp.Model.MyNavigationSource;
import com.example.waz.Mvp.Model.MyProjectSource;
import com.example.waz.UI.Bean.NavigationBean;
import com.example.waz.UI.Bean.ProjectBean;
import com.trello.rxlifecycle2.components.support.RxFragment;

public class MyProjectPresenter implements ProjectConstrat.ProjectPresenter {
    private final ProjectConstrat.ProjectSource source;
    private ProjectConstrat.ProjectView views;

    public MyProjectPresenter() {
        source = new MyProjectSource();
    }

    @Override
    public void setProject() {
        if (source != null) {
            source.getProject((RxFragment) views, new BaseCallBack<ProjectBean>() {
                @Override
                public void onSuccese(ProjectBean data) {
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
    public void attachView(ProjectConstrat.ProjectView view) {
        views = view;
    }

    @Override
    public void detachView(ProjectConstrat.ProjectView view) {
        views = null;
    }
}
