package com.ltyy.chatgpt.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ltyy.chatgpt.app.AppApplication;

public class ToastUtils {
    private static Toast mToast;

    public static void showToast(int res) {
        showToastMessage(AppApplication.getContext().getResources().getString(res), Toast.LENGTH_LONG);
    }

    public static void showToast(String msg) {
        showToastMessage(msg, Toast.LENGTH_LONG);
    }

    @SuppressLint("InflateParams")
    public static void showToastMessage(String str, int length) {
        if (TextUtils.isEmpty(str)) return;
        //android 11（sdk版本30）之后不支持自定义toast，只能通过makeText调用
        if (Build.VERSION.SDK_INT >= 30 /** Build.VERSION_CODES.R **/){
            Toast.makeText(AppApplication.getContext(), str, length).show();
        }else {
            if (mToast == null) {
                mToast = Toast.makeText(AppApplication.getContext(), str, length);
                mToast.setGravity(Gravity.CENTER, 0, 0);
                LinearLayout layout = (LinearLayout) mToast.getView();
                TextView toastView = (TextView) layout.getChildAt(0);
                toastView.setTextSize(26);
            } else {
                mToast.setText(str);
            }
            mToast.show();
        }
    }
}
