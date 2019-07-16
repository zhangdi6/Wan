package com.example.waz.Base;

import android.content.Context;

public interface BaseView <T extends BasePresenter>{
    void setPresenter(T presenter);
    Context getContextObject();
}
