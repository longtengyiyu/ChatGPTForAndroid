package com.ltyy.chatgpt.app;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.ltyy.chatgpt.utils.SharedPreferencesUtils;

public class AppApplication extends MultiDexApplication {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        SharedPreferencesUtils.getInstance().init(context);
    }

    public static Context getContext(){
        return context;
    }
}
