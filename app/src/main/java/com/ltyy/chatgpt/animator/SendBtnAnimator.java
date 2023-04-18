package com.ltyy.chatgpt.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.ltyy.chatgpt.widget.ViewUtils;

public class SendBtnAnimator implements ISendBtnAnimator{
    @Override
    public void showAnimator(View v) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationX", v.getWidth(), 0);
        animator.setDuration(200);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                ViewUtils.setVisible(v);
            }
        });
        animator.start();
    }

    @Override
    public void hideAnimator(View v) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationX", 0, v.getWidth());
        animator.setDuration(200);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ViewUtils.setGone(v);
            }
        });
        animator.start();
    }
}
