package com.ltyy.chatgpt.utils;

import android.animation.ObjectAnimator;
import android.view.View;

public class FlashingAnimatorUtils {

    private static ObjectAnimator animator;
    public static void flash(View v){
        animator = ObjectAnimator.ofFloat(v, "alpha", 1, 0, 1);
        animator.setDuration(1000);
        animator.setRepeatCount(-1);
        animator.start();
    }

    public static void stopFlash(){
        if (animator != null){
            animator.cancel();
        }
    }

}
