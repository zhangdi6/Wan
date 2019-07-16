package com.example.waz.Base;

public interface BaseCallBack<T>{
    void onSuccese(T data);
    void onFailed(String res);
}
