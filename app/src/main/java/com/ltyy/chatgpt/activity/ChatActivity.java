package com.ltyy.chatgpt.activity;

import android.content.Context;
import android.content.Intent;

import com.ltyy.chatgpt.base.BaseViewDataBindingActivity;
import com.ltyy.chatgpt.databinding.ActivityChatBinding;

public class ChatActivity extends BaseViewDataBindingActivity<ActivityChatBinding> {

    public static void startChatActivity(Context context){
        context.startActivity(new Intent(context, ChatActivity.class));
    }

    @Override
    protected int getContentLayoutRes() {
        return 0;
    }
}
