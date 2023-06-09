package com.ltyy.chatgpt.model;

import com.ltyy.chatgpt.db.Repository;
import com.ltyy.chatgpt.entity.Chat;
import com.ltyy.chatgpt.entity.Group;

import java.util.List;

import io.reactivex.Observer;

public class GroupModel {

    public void getGroupList(Observer<List<Group>> observer){
        Repository.getInstance().getGroupList().subscribe(observer);
    }

    public void removeGroupById(Observer<Boolean> observer, long id){
        Repository.getInstance().removeGroupById(id).subscribe(observer);
    }

    public void getChatList(Observer<List<Chat>> observer, long groupId){
        Repository.getInstance().getChatListByGroupId(groupId).subscribe(observer);
    }

    public void removeChatById(Observer<Boolean> observer, long id){
        Repository.getInstance().removeChatById(id).subscribe(observer);
    }
}
