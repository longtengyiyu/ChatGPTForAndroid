package com.ltyy.chatgpt.viewmodel;

import com.ltyy.chatgpt.base.BaseViewModel;
import com.ltyy.chatgpt.bean.Message;
import com.ltyy.chatgpt.bean.Prompt;
import com.ltyy.chatgpt.bean.PromptRes;
import com.ltyy.chatgpt.entity.Chat;
import com.ltyy.chatgpt.entity.Group;
import com.ltyy.chatgpt.model.ChatModel;
import com.ltyy.chatgpt.param.PromptParams;
import com.ltyy.chatgpt.utils.JsonUtils;
import com.ltyy.chatgpt.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class ChatViewModel extends BaseViewModel<String> {

    private ChatModel chatModel;
    public ChatViewModel(){
        chatModel = new ChatModel();
    }
    private PromptParams params;
    private List<Message> messages = new ArrayList<>();
    private long chatGroupId;
    private boolean groupHasSave = false;

    public void getGroupId(){
        chatModel.getGroupId(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Long aLong) {
                chatGroupId = aLong;
                LogUtils.d(TAG, "group id -->" + chatGroupId);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void loadPrompt(String prompt){
        chatModel.loadPrompt(getData(), getErrorMsg(), getPromptParams(prompt));
    }

    private PromptParams getPromptParams(String prompt){
        if (params == null){
            params = new PromptParams();
        }
        Message message = new Message();
        message.setContent(prompt);
        if (messages.size() >= 2){
            messages.remove(0);
        }
        messages.add(message);
        //base context
        params.setMessages(messages);
        LogUtils.d(TAG, "params -->" + JsonUtils.get().toJson(params));
        return params;
    }

    public void save(Chat chat){
        saveChat(chat);
        saveGroup(chat.getContent());
    }

    public void saveChat(Chat chat){
        chat.setGroupId(chatGroupId);
        chatModel.saveChat(new Observer<Boolean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Boolean aBoolean) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, chat);
    }

    private void saveGroup(String content){
        if (!groupHasSave){
            groupHasSave = true;
            Group group = new Group();
            group.setContent(content);
            group.setId(chatGroupId);
            group.setTimestamp(System.currentTimeMillis());
            chatModel.saveGroup(new Observer<Boolean>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull Boolean aBoolean) {
                    LogUtils.i(TAG, "insert group successful");
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    LogUtils.i(TAG, "insert group failed");
                }

                @Override
                public void onComplete() {

                }
            },group);
        }
    }
}
