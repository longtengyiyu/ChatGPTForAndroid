package com.ltyy.chatgpt.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ltyy.chatgpt.R;
import com.ltyy.chatgpt.adapter.ChatAdapter;
import com.ltyy.chatgpt.animator.SendBtnAnimator;
import com.ltyy.chatgpt.app.AppConstants;
import com.ltyy.chatgpt.base.BaseMVVMActivity;
import com.ltyy.chatgpt.databinding.ActivityChatBinding;
import com.ltyy.chatgpt.entity.Chat;
import com.ltyy.chatgpt.utils.CommonUtils;
import com.ltyy.chatgpt.utils.LogUtils;
import com.ltyy.chatgpt.utils.NetworkUtils;
import com.ltyy.chatgpt.utils.TextCheckedUtils;
import com.ltyy.chatgpt.utils.ToastUtils;
import com.ltyy.chatgpt.viewmodel.ChatViewModel;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class ChatActivity extends BaseMVVMActivity<ChatViewModel, ActivityChatBinding, String> {

    private ChatAdapter adapter;
    private boolean isInput;
    private SendBtnAnimator animator = new SendBtnAnimator();
    private Disposable disposable;

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
        binding.recyclerView.setOnTouchListener((v, event) -> {
            CommonUtils.hideSoftInput(this);
            return false;
        });
        transparentNav(this);
        viewModel.getGroupId();
        animator.hideAnimator(binding.tvSend);
        textChanged();
        initRy();
        addTips();
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("");
    }

    public void transparentNav(Activity activity) {
        setTransparentStatus(true, activity);
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.color_0968);
    }

    private void setTransparentStatus(boolean on, Activity activity) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams params = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on)
            params.flags |= bits;
        else
            params.flags &= ~bits;

        win.setAttributes(params);
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

    private void scrollBottom(){
//        binding.recyclerView.postDelayed(() ->
//                        binding.recyclerView.scrollToPosition(adapter.getItemCount()-1),
//                200);
        binding.recyclerView.scrollToPosition(adapter.getItemCount()-1);
    }

    private void textChanged(){
        binding.msgSay.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus){
                LogUtils.d(TAG, "hasFocus");
                scrollBottom();
            }
        });
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

    public void addInput(){
        binding.tvSend.postDelayed(() -> {
            Chat chat = new Chat();
            chat.setType(Chat.TYPE_INPUT);
            chat.setContent("");
            adapter.addItem(chat);
        }, 500);
    }

    public void send(View v){
        if (NetworkUtils.isNetworkAvailable(ChatActivity.this)){
            String content = binding.msgSay.getText().toString();
            CommonUtils.hideSoftInput(this);
            if (TextCheckedUtils.checked(content)){
                binding.msgSay.setText("");
                binding.tvSend.setEnabled(false);
                Chat chat = new Chat();
                chat.setType(Chat.TYPE_USER);
                chat.setContent(content);
                viewModel.save(chat);
                adapter.addItem(chat);
                addInput();
                clickable();
                viewModel.loadPrompt(content);
            }
        }else {
            ToastUtils.showToast(R.string.toast_network_error);
        }
    }

    private void clickable(){
        stop();
        disposable = Observable.timer(AppConstants.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> addResChat(getString(R.string.time_out)));
    }

    private void stop(){
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }

    @Override
    protected void onResponseSuccess(String s) {
        stop();
        addResChat(s);
    }

    private void addResChat(String content){
        binding.tvSend.setEnabled(true);
        int position = adapter.getItemCount() - 1;
        Chat chat = adapter.getItemData(position);
        chat.setType(Chat.TYPE_AI);
        chat.setContent(chat.getContent() + content.replaceAll("\n", ""));
        viewModel.saveChat(chat);
        adapter.notifyItemChanged(position);
        scrollBottom();
    }

    @Override
    protected void onResponseFail(String msg) {
        stop();
        binding.tvSend.setEnabled(true);
        addResChat(getString(R.string.time_out));
        scrollBottom();
    }

    private void back(View v){
        stop();
        finish();
    }
}
