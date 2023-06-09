package com.ltyy.chatgpt.model;

import androidx.lifecycle.MutableLiveData;

import com.ltyy.chatgpt.api.ApiMethods;
import com.ltyy.chatgpt.api.RequestService;
import com.ltyy.chatgpt.bean.PromptRes;
import com.ltyy.chatgpt.db.Repository;
import com.ltyy.chatgpt.entity.Chat;
import com.ltyy.chatgpt.entity.Group;
import com.ltyy.chatgpt.param.PromptParams;

import io.reactivex.Observer;

public class ChatModel {
    public void loadPrompt(MutableLiveData<String> data, MutableLiveData<String> error,PromptParams param){
//        ApiMethods.getInstance().getPrompt(observer, param);
        RequestService.getInstance().getPrompt(data, error, param);
    }

    public void getGroupId(Observer<Long> observer){
        Repository.getInstance().getChatGroupId().subscribe(observer);
    }

    public void saveChat(Observer<Boolean> observer, Chat chat){
        Repository.getInstance().saveChat(chat).subscribe(observer);
    }

    public void saveGroup(Observer<Boolean> observer, Group group){
        Repository.getInstance().insertGroup(group).subscribe(observer);
    }
}
