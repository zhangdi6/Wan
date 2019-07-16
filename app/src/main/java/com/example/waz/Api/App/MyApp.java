package com.example.waz.Api.App;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.waz.dao.DaoMaster;
import com.example.waz.dao.DaoSession;

public class MyApp extends Application {
    public  static Context mContext ;
    public static synchronized Context getContext(){
        return mContext;
    }
    private static DaoSession daoSession;
    private static MyApp sBaseApp;
    //默认不是夜间模式
    public static int mMode = AppCompatDelegate.MODE_NIGHT_NO;
    public static int mWidthPixels;
    public static int mHeightPixels;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        sBaseApp = this;
        getScreenWH();
        initData();
    }

    private void initData() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.
                DevOpenHelper(this, "qwck.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();


    }
    private void getScreenWH() {
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display defaultDisplay = manager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        defaultDisplay.getMetrics(metrics);
        mWidthPixels = metrics.widthPixels;
        mHeightPixels = metrics.heightPixels;
    }
    public static MyApp getInstance(){
        return sBaseApp;
    }
    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
