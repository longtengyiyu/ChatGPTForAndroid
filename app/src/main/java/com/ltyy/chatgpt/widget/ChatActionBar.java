package com.ltyy.chatgpt.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.ltyy.chatgpt.databinding.WidgetActionBarBinding;


public class ChatActionBar extends LinearLayout {

    private WidgetActionBarBinding binding;
    public ChatActionBar(@NonNull Context context) {
        this(context, null);
    }

    public ChatActionBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatActionBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        binding = WidgetActionBarBinding.inflate(layoutInflater, this, true);
        binding.ivBack.setOnClickListener(v -> ((Activity)context).finish());
    }

    public void hideBack(boolean isHide){
        if (isHide){
            ViewUtils.setGone(binding.ivBack);
        }else {
            ViewUtils.setVisible(binding.ivBack);
        }
    }

    public void hideSetting(boolean isHide){
        if (isHide){
            ViewUtils.setGone(binding.ivSetting);
        }else {
            ViewUtils.setVisible(binding.ivSetting);
        }
    }

    public AppCompatImageView getSetting(){
        return binding.ivSetting;
    }

    public void setTitle(String title) {
        binding.tvName.setText(title);
    }

    public void setTitle(int res) {
        binding.tvName.setText(res);
    }
}
