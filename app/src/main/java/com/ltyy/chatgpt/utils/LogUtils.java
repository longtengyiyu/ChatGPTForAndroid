package com.ltyy.chatgpt.utils;

import android.util.Log;

import com.ltyy.chatgpt.BuildConfig;

public class LogUtils {

    public static void d(String tag, String msg){
        if (BuildConfig.DEBUG){
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg){
        if (BuildConfig.DEBUG){
            Log.i(tag, msg);
        }
    }

    public static void e(String tag, String msg){
        if (BuildConfig.DEBUG){
            Log.e(tag, msg);
        }
    }
}
