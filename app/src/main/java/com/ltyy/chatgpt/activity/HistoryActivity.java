package com.ltyy.chatgpt.activity;

import android.content.Context;
import android.content.Intent;

import com.ltyy.chatgpt.R;
import com.ltyy.chatgpt.base.BaseViewDataBindingActivity;
import com.ltyy.chatgpt.databinding.ActivityHistoryBinding;

public class HistoryActivity extends BaseViewDataBindingActivity<ActivityHistoryBinding> {

    public static void startHistoryActivity(Context context){
        context.startActivity(new Intent(context, HistoryActivity.class));
    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.activity_history;
    }
}
