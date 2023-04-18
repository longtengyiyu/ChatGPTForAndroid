package com.ltyy.chatgpt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ltyy.chatgpt.R;
import com.ltyy.chatgpt.adapter.ChatAdapter;
import com.ltyy.chatgpt.app.AppConstants;
import com.ltyy.chatgpt.base.BaseMVVMActivity;
import com.ltyy.chatgpt.databinding.ActivityChatHistoryBinding;
import com.ltyy.chatgpt.dialog.DeleteDialog;
import com.ltyy.chatgpt.entity.Chat;
import com.ltyy.chatgpt.utils.LogUtils;
import com.ltyy.chatgpt.viewmodel.HistoryChatViewModel;

import java.util.List;

public class ChatHistoryActivity extends BaseMVVMActivity<HistoryChatViewModel, ActivityChatHistoryBinding, List<Chat>> {

    private ChatAdapter adapter;
    private DeleteDialog dialog;
    private int currentPosition;

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
        adapter.setOnItemClickListener(position ->{
            currentPosition = position;
            if (currentPosition >= 0){
                getDialog().show(ChatHistoryActivity.this.getSupportFragmentManager(), "deleteChatDialog");
            }
        });
    }

    private DeleteDialog getDialog(){
        if (dialog == null){
            dialog = DeleteDialog.newInstance();
            dialog.setListener(() -> {
                Chat chat = adapter.getItemData(currentPosition);
                long chatId = chat.getId();
                viewModel.removeChatById(chatId);
                getDialog().dismissAllowingStateLoss();
                adapter.removeItem(currentPosition);
            });
        }
        return dialog;
    }

    @Override
    protected void onResponseSuccess(List<Chat> chats) {
        adapter.addItems(chats);
    }

    @Override
    protected void onResponseFail(String msg) {

    }
}
