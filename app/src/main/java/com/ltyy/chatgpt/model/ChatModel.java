package com.ltyy.chatgpt.model;

import com.ltyy.chatgpt.api.ApiMethods;
import com.ltyy.chatgpt.bean.PromptRes;
import com.ltyy.chatgpt.param.PromptParams;

import io.reactivex.Observer;

public class ChatModel {
    public void loadPrompt(Observer<PromptRes> observer, PromptParams param){
        ApiMethods.getInstance().getPrompt(observer, param);
    }
}
