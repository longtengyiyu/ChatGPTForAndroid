package com.ltyy.chatgpt.db;

import com.ltyy.chatgpt.entity.Chat;
import com.ltyy.chatgpt.entity.Group;

import java.util.List;

import io.reactivex.Observable;

public interface IRepository {

    /**
     * 获取group id
     * @return
     */
    Observable<Long> getChatGroupId();
    /**
     * save chat message
     * @param chat
     * @return
     */
    Observable<Boolean> saveChat(Chat chat);


    /**
     * insert group
     * @param group
     * @return
     */
    Observable<Boolean> insertGroup(Group group);

    /**
     * get group list
     * @return
     */
    Observable<List<Group>> getGroupList();

    /**
     * get chat message list
     * @return
     */
    Observable<List<Chat>> getChatListByGroupId(long groupId);

    /**
     * remove group
     * @param groupId
     * @return
     */
    Observable<Boolean> removeGroupById(long groupId);

    /**
     * remove chat
     * @param chat
     * @return
     */
    Observable<Boolean> removeChatById(long chat);
}
