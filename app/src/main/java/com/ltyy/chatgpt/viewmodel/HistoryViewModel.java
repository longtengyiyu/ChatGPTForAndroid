package com.ltyy.chatgpt.viewmodel;

import com.ltyy.chatgpt.base.BaseViewModel;
import com.ltyy.chatgpt.entity.Group;
import com.ltyy.chatgpt.model.GroupModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class HistoryViewModel extends BaseViewModel<List<Group>> {
    private GroupModel model;

    public HistoryViewModel(){
        model = new GroupModel();
    }

    public void getGroupList(){
        model.getGroupList(new Observer<List<Group>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<Group> groups) {
                getData().postValue(groups);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getErrorMsg().postValue(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void removeGroupById(long id){
        model.removeGroupById(new Observer<Boolean>() {
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
        }, id);
    }
}
