package com.ltyy.chatgpt.viewmodel;

import com.ltyy.chatgpt.base.BaseViewModel;
import com.ltyy.chatgpt.entity.Chat;
import com.ltyy.chatgpt.entity.Group;
import com.ltyy.chatgpt.model.GroupModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class HistoryChatViewModel extends BaseViewModel<List<Chat>> {
    private GroupModel model;

    public HistoryChatViewModel(){
        model = new GroupModel();
    }

    public void getChatListByGroupId(long groupId){
        model.getChatList(new Observer<List<Chat>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<Chat> chats) {
                getData().postValue(chats);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getErrorMsg().postValue(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }, groupId);
    }
}
