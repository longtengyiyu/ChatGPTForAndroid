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
import com.ltyy.chatgpt.base.BaseMVVMActivity;
import com.ltyy.chatgpt.databinding.ActivityChatBinding;
import com.ltyy.chatgpt.entity.Chat;
import com.ltyy.chatgpt.utils.CommonUtils;
import com.ltyy.chatgpt.utils.TextCheckedUtils;
import com.ltyy.chatgpt.viewmodel.ChatViewModel;

public class ChatActivity extends BaseMVVMActivity<ChatViewModel, ActivityChatBinding, String> {

    private ChatAdapter adapter;
    private boolean isInput;
    private SendBtnAnimator animator = new SendBtnAnimator();

    public static void startChatActivity(Context context){
        context.startActivity(new Intent(context, ChatActivity.class));
    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.activity_chat;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.chatBar.setTitle(R.string.chat);
        binding.chatBar.hideBack(false);
        binding.recyclerView.setOnTouchListener((v, event) -> {
            CommonUtils.hideSoftInput(this);
            return false;
        });
        animator.hideAnimator(binding.tvSend);
        textChanged();
        initRy();
        addTips();
    }

    @Override
    protected Class<ChatViewModel> getModelClass() {
        return ChatViewModel.class;
    }

    private void initRy(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerView.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(this);
        binding.recyclerView.setAdapter(adapter);
    }

    private void textChanged(){
        binding.msgSay.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)){
                    if (isInput){
                        isInput = false;
                        animator.hideAnimator(binding.tvSend);
                    }
                }else {
                    if (!isInput){
                        isInput = true;
                        animator.showAnimator(binding.tvSend);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void addTips(){
        Chat chat = new Chat();
        chat.setType(Chat.TYPE_TIPS);
        chat.setContent(getString(R.string.tips_chat));
        adapter.addItem(chat);
    }

    public void send(View v){
        String content = binding.msgSay.getText().toString();
        CommonUtils.hideSoftInput(this);
        if (TextCheckedUtils.checked(content)){
            binding.msgSay.setText("");
            binding.tvSend.setEnabled(false);
            Chat chat = new Chat();
            chat.setType(Chat.TYPE_USER);
            chat.setContent(content);
            adapter.addItem(chat);
            viewModel.loadPrompt(content);
        }
    }

}
