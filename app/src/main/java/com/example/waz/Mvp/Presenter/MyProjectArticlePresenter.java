package com.example.waz.Mvp.Presenter;

import android.content.Intent;

import com.example.waz.Base.BaseCallBack;
import com.example.waz.Mvp.Contract.ProjectArticleConstrat;
import com.example.waz.Mvp.Model.MyProjectArticleSource;
import com.example.waz.UI.Bean.ProjectArticleBean;
import com.trello.rxlifecycle2.components.support.RxFragment;

public class MyProjectArticlePresenter implements ProjectArticleConstrat.ProjectArticlePresenter {
    private final ProjectArticleConstrat.ProjectArticleSource source;
    private ProjectArticleConstrat.ProjectArticleView views;
    public MyProjectArticlePresenter() {
        source = new MyProjectArticleSource();
    }
    @Override
    public void setProjectArticle(int page,int cid) {
        if (source != null) {
            source.getProjectArticle(page,cid,(RxFragment) views, new BaseCallBack<ProjectArticleBean>() {
                @Override
                public void onSuccese(ProjectArticleBean data) {
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
    public void attachView(ProjectArticleConstrat.ProjectArticleView view) {
        views = view;
    }

    @Override
    public void detachView(ProjectArticleConstrat.ProjectArticleView view) {
        views = null;

    }
}
