package com.ltyy.chatgpt.viewmodel;

import com.ltyy.chatgpt.base.BaseViewModel;
import com.ltyy.chatgpt.bean.Message;
import com.ltyy.chatgpt.bean.Prompt;
import com.ltyy.chatgpt.bean.PromptRes;
import com.ltyy.chatgpt.model.ChatModel;
import com.ltyy.chatgpt.param.PromptParams;

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
    private Message message;

    public void loadPrompt(String prompt){
        chatModel.loadPrompt(new Observer<PromptRes>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull PromptRes promptRes) {
                if (promptRes != null && promptRes.getChoices() != null &&
                        !promptRes.getChoices().isEmpty()){
                    Prompt prompt = promptRes.getChoices().get(0);
                    if (prompt != null){
                        Message message = prompt.getMessage();
                        if (message != null){
                            String content = message.getContent();
                            getData().postValue(content);
                        }
                    }
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
        if (message == null){
            message = new Message();
        }
        message.setContent(prompt);
        messages.add(message);
        params.setMessages(messages);
        return params;
    }
}
