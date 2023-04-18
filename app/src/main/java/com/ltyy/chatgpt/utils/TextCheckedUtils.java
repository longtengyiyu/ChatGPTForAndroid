package com.ltyy.chatgpt.utils;

import android.text.TextUtils;

import com.ltyy.chatgpt.R;

public class TextCheckedUtils {

    public static boolean checked(String apiKey){
        if (TextUtils.isEmpty(apiKey)){
            ToastUtils.showToast(R.string.toast_empty);
            return false;
        }
        return true;
    }
}
