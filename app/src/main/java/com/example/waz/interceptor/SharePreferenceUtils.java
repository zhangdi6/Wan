package com.example.waz.interceptor;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class SharePreferenceUtils {
    /**
     * 保存数据到文件中
     *
     * @param context
     *            上下文对象
     * @param fileName
     *            xml的文件名
     * @param key
     *            保存的key的值
     * @param value
     *            保存的value的值
     * @throws Exception
     *             出现的异常，值得类型不支持
     */
    public static void put(Context context, String fileName, String key, Object value) {
        // 获取SP对象，只能读取数据
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        // 获取editor对象
        SharedPreferences.Editor editor = sp.edit();

        // 判断value是什么类型
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Set) {
            editor.putStringSet(key, (Set<String>) value);
        }
        // 提交保存数据--data-data-应用包名-shared_prefs 目录下
        editor.commit();
    }

    public static Object get(Context context, String fileName, String key, Object defaultValue) {
        // 获取SP对象，只能读取数据
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        // 判断defaultValue是什么类型
        if (defaultValue instanceof String) {
            return sp.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return sp.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Float) {
            return sp.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Long) {
            return sp.getLong(key, (Long) defaultValue);
        } else if (defaultValue instanceof Set) {
            return sp.getStringSet(key, (Set<String>) defaultValue);
        }
        return null;
    }

    /**
     * 移除指定的key
     * @param context	上下文对象
     * @param fileName	xml文件名
     * @param key		要移除的key
     */
    public static void remove(Context context, String fileName, String key) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    public static boolean contains(Context context, String fileName, String key){
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.contains(key);
    }
}
