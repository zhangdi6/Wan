package com.example.waz.Api;

import android.util.Log;

import com.example.waz.Api.App.MyApp;
import com.example.waz.UI.Bean.MyBean;
import com.example.waz.dao.DaoSession;
import com.example.waz.dao.MyBeanDao;

import java.util.HashMap;
import java.util.List;

public class Utils {
    private static final String TAG = "Utils";
    private static DaoSession mDaoSession = MyApp.getDaoSession();
    private HashMap<Integer,Integer>map=new
            HashMap<>();

    public static long insert(MyBean bean) {
        if (!isExists(bean)) {
            return mDaoSession.insert(bean);
        } else {
            Log.d(TAG, "insert: 已存在");
        }
        return 0;
    }

    public static boolean deleteItem(MyBean bean) {
        boolean flag;
        if (isExists(bean)) {
            mDaoSession.delete(bean);
            flag = true;
        } else {
            flag = false;
            Log.d(TAG, "deleteItem: 不存在");
        }
        return flag;
    }

    public static List<MyBean> queryAll() {
        return mDaoSession.loadAll(MyBean.class);
    }

    public static boolean upDataItem(MyBean bean) {
        boolean flag;

        if (isExists(bean)) {
            mDaoSession.update(bean);
            flag = true;
        } else {
            flag = false;
            Log.d(TAG, "upDataItem: 不存在");
        }
        return flag;
    }

    public static boolean isExists(MyBean bean) {
        MyBean tabBean = mDaoSession.queryBuilder(MyBean.class)
                .where(MyBeanDao.Properties.Title.eq(bean.getTitle()))
                .build().unique();
        return tabBean != null ? true : false;
    }

}
