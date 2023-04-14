package com.ltyy.chatgpt.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.ltyy.chatgpt.utils.AppDpiUtils;
import com.ltyy.chatgpt.utils.SharedPreferencesUtils;

public abstract class BaseAbstractActivity extends AppCompatActivity {

    protected final String TAG =  getClass().getSimpleName();
    private SharedPreferencesUtils sPreferencesUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        hideSystemActionBar();
    }

    private void beforeSetContentView() {
        AppDpiUtils.setCustomDensity(getResources());
    }

    protected SharedPreferencesUtils getSharedPreferencesUtils(){
        if (sPreferencesUtils == null){
            sPreferencesUtils = SharedPreferencesUtils.getInstance();
        }
        return sPreferencesUtils;
    }

    private void hideSystemActionBar(){
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }
    }

    protected abstract int getContentLayoutRes();
}
