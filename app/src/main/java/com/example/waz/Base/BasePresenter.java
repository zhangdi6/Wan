package com.example.waz.Base;

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);
    void detachView(T view);
}
