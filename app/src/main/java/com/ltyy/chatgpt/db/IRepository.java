package com.ltyy.chatgpt.db;

import com.ltyy.chatgpt.entity.Chat;

import java.util.List;

import io.reactivex.Observable;

public interface IRepository {

    /**
     * 获取group id
     * @return
     */
    Observable<Integer> getChatGroupId();
    /**
     * save chat message
     * @param chat
     * @return
     */
    Observable<Boolean> saveChat(Chat chat);

    /**
     * get chat message list
     * @return
     */
    Observable<List<Chat>> getChatListByGroupId(String name);
}
