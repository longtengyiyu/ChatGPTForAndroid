package com.ltyy.chatgpt.viewmodel;

import com.google.gson.Gson;
import com.ltyy.chatgpt.base.BaseViewModel;
import com.ltyy.chatgpt.bean.PromptRes;
import com.ltyy.chatgpt.model.ChatModel;
import com.ltyy.chatgpt.param.PromptParams;
import com.ltyy.chatgpt.utils.LogUtils;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class ChatViewModel extends BaseViewModel<String> {

    private ChatModel chatModel;
    public ChatViewModel(){
        chatModel = new ChatModel();
    }
    private PromptParams params;

    public void loadPrompt(String prompt){
        chatModel.loadPrompt(new Observer<PromptRes>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull PromptRes promptRes) {
                if (promptRes != null){
                    LogUtils.d(TAG, new Gson().toJson(promptRes));
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, getPromptParams(prompt));
    }

    private PromptParams getPromptParams(String prompt){
        if (params == null){
            params = new PromptParams();
        }
        params.setPrompt(prompt);
        return params;
    }
}
