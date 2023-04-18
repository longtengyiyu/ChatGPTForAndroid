package com.ltyy.chatgpt.widget;

import android.view.View;

public class ViewUtils {
    public static void setGone(View... views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    public static void setVisible(View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }
}
