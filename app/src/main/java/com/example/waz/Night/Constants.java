package com.example.waz.Night;

import android.os.Environment;

import com.example.waz.Api.App.MyApp;

import java.io.File;

public interface Constants {
    //是否为debug状态,正式上线版本需要改为false
    boolean isDebug = true;

    String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() +
            File.separator + "codeest" + File.separator + "GeekNews";

    String FILE_PROVIDER_AUTHORITY="com.baidu.geek.fileprovider";

    //网络缓存的地址
    String PATH_DATA = MyApp.getInstance().getCacheDir().getAbsolutePath() +
            File.separator + "data";

    String PATH_CACHE = PATH_DATA + "/NetCache";

    String NAME = "name";
    String PSW = "psw";
    String USERNAME = "username";
    String LOGIN = "login";


    //是否为debug状态,正式上线版本需要改为false
//    boolean isDebug = true;
//
//
//    String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() +
//            File.separator + "codeest" + File.separator + "GeekNews";
//
//    String FILE_PROVIDER_AUTHORITY = "com.baidu.geek.fileprovider";
//
//    //网络缓存的地址
//    String PATH_DATA = BaseApp.getInstance().getCacheDir().getAbsolutePath() +
//            File.separator + "data";
//
//    String PATH_CACHE = PATH_DATA + "/NetCache";
    String DATA = "data";

    String USER_NAME = "userName";
    boolean ifmap = true;
    String SEARCH_CONTENT = "search_content";
    String PASSWORD = "password";

    String NIGHT = "night";
    String DATASBEAN = "datasbean";

//    String DATA = "data";
    //夜间模式
    String MODE = "mode";
    String NIGHT_CURRENT_FRAG_POS = "fragment_pos";
    //保存设置日夜间模式时碎片的position
    String DAY_NIGHT_FRAGMENT_POS = "day_night_fragment_pos";

    //设置界面的图片是否保存key值
    String SETTING_NO_IMAGE = "setting_no_image";
    int ERROR_CODE = -1001;
}