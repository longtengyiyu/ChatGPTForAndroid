package com.ltyy.chatgpt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ltyy.chatgpt.R;
import com.ltyy.chatgpt.adapter.ChatAdapter;
import com.ltyy.chatgpt.animator.SendBtnAnimator;
import com.ltyy.chatgpt.app.AppConstants;
import com.ltyy.chatgpt.base.BaseMVVMActivity;
import com.ltyy.chatgpt.databinding.ActivityChatBinding;
import com.ltyy.chatgpt.databinding.ActivityChatHistoryBinding;
import com.ltyy.chatgpt.entity.Chat;
import com.ltyy.chatgpt.utils.CommonUtils;
import com.ltyy.chatgpt.utils.LogUtils;
import com.ltyy.chatgpt.utils.NetworkUtils;
import com.ltyy.chatgpt.utils.TextCheckedUtils;
import com.ltyy.chatgpt.utils.ToastUtils;
import com.ltyy.chatgpt.viewmodel.ChatViewModel;
import com.ltyy.chatgpt.viewmodel.HistoryChatViewModel;
import com.ltyy.chatgpt.viewmodel.HistoryViewModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class ChatHistoryActivity extends BaseMVVMActivity<HistoryChatViewModel, ActivityChatHistoryBinding, List<Chat>> {

    private ChatAdapter adapter;

    public static void startChatHistoryActivity(Context context, long groupId){
        Intent intent = new Intent(context, ChatHistoryActivity.class);
        intent.putExtra(AppConstants.GROUP_ID, groupId);
        context.startActivity(intent);
    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.activity_chat_history;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.chatBar.setTitle(R.string.history_chat);
        binding.chatBar.hideBack(false);
        initRy();
        if (getIntent() != null){
            long groupId = getIntent().getLongExtra(AppConstants.GROUP_ID, 0);
            LogUtils.d(TAG, "groupId:" + groupId);
            viewModel.getChatListByGroupId(groupId);
        }
    }

    @Override
    protected Class<HistoryChatViewModel> getModelClass() {
        return HistoryChatViewModel.class;
    }

    private void initRy(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerView.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(this);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResponseSuccess(List<Chat> chats) {
        adapter.addItems(chats);
    }

    @Override
    protected void onResponseFail(String msg) {

    }
}
