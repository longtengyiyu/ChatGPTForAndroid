package com.ltyy.chatgpt.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static boolean isNetworkAvailable(Context context){
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                LogUtils.d(TAG, "当前是否有网络：" + mNetworkInfo.isAvailable());
                return mNetworkInfo.isAvailable();
            }
        }
        LogUtils.d(TAG, "当前是否有网络：false");
        return false;

    }
}
