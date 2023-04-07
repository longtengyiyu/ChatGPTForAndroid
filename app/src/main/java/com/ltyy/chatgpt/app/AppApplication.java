package com.ltyy.chatgpt.app;

import android.content.Context;
import android.text.TextUtils;

import androidx.multidex.MultiDexApplication;

import com.ltyy.chatgpt.activity.SettingActivity;
import com.ltyy.chatgpt.api.ApiMethods;
import com.ltyy.chatgpt.db.ChatDaoHelper;
import com.ltyy.chatgpt.utils.SharedPreferencesUtils;

public class AppApplication extends MultiDexApplication {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        context = getApplicationContext();
        SharedPreferencesUtils.getInstance().init(context);
        ChatDaoHelper.create(getContext());
        String apiKey = SharedPreferencesUtils.getInstance().getString(AppSPContact.SP_PARAM_API_KEY);
        if (TextUtils.isEmpty(apiKey)){
            ApiMethods.instance(context);
        }
    }

    public static Context getContext(){
        return context;
    }
}
