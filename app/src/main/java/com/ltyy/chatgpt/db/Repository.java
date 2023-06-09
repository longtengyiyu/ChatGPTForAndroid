package com.ltyy.chatgpt.db;


import com.ltyy.chatgpt.entity.Chat;
import com.ltyy.chatgpt.entity.Group;
import com.ltyy.chatgpt.greendao.ChatDao;
import com.ltyy.chatgpt.greendao.DaoSession;
import com.ltyy.chatgpt.greendao.GroupDao;
import com.ltyy.chatgpt.utils.JsonUtils;
import com.ltyy.chatgpt.utils.LogUtils;

import java.util.List;

import io.reactivex.Emitter;
import io.reactivex.Observable;

/**
 * Author:    Oscar
 * Version    V1.0
 * Date:      2022/3/29
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2022/3/29      Oscar            1.0                    1.0
 * Why & What is modified:
 */
public class Repository implements IRepository{

  private static final String TAG = Repository.class.getSimpleName();
  private volatile static Repository INSTANCE = null;
  private DaoSession daoSession;

  private Repository(){
    daoSession = ChatDaoHelper.instance().getDaoSession();
  }

  public static Repository getInstance(){
    if (INSTANCE == null){
      synchronized (Repository.class){
        INSTANCE = new Repository();
      }
    }
    return INSTANCE;
  }

  private <T> void objNextOrErr(Emitter<T> emitter, T obj){
    if(obj != null){
      emitter.onNext(obj);
      emitter.onComplete();
    }else{
      emitter.onError(new Exception("result is null"));
    }
  }

  @Override
  public Observable<Long> getChatGroupId() {
    return Observable.create(e -> {
      GroupDao dao = daoSession.getGroupDao();
      Group group = dao.queryBuilder().orderDesc(GroupDao.Properties.Id).limit(1).unique();
      if (group == null){
        e.onNext(0L);
      }else{
        long id = group.getId() + 1;
        e.onNext(id);
      }
      e.onComplete();
    });
  }

  @Override
  public Observable<Boolean> saveChat(Chat chat) {
    return Observable.create(e -> {
      ChatDao dao = daoSession.getChatDao();
      dao.insert(chat);
    });
  }

  @Override
  public Observable<Boolean> insertGroup(Group group) {
    return Observable.create(e -> {
      GroupDao dao = daoSession.getGroupDao();
      dao.insertInTx(group);
      e.onNext(Boolean.TRUE);
      e.onComplete();
    });
  }

  @Override
  public Observable<List<Group>> getGroupList() {
    return Observable.create(e -> {
      GroupDao dao = daoSession.getGroupDao();
      List<Group> groupList = dao.
              queryBuilder().
              where(GroupDao.Properties.Status.eq(0)).
              orderDesc(GroupDao.Properties.Id).
              list();
      LogUtils.d(TAG, "groupList --> " + JsonUtils.get().toJson(groupList));
      e.onNext(groupList);
      e.onComplete();
    });
  }

  @Override
  public Observable<List<Chat>> getChatListByGroupId(long groupId) {
    return Observable.create(e -> {
      ChatDao dao = daoSession.getChatDao();
      List<Chat> chatList = dao.
              queryBuilder().
              where(ChatDao.Properties.GroupId.eq(groupId),
                    ChatDao.Properties.Status.eq(0)).
              list();
      LogUtils.d(TAG, "chatList --> " + JsonUtils.get().toJson(chatList));
      e.onNext(chatList);
      e.onComplete();
    });
  }

  @Override
  public Observable<Boolean> removeGroupById(long groupId) {
    return Observable.create(e -> {
      updateGroupById(groupId);
      e.onNext(Boolean.TRUE);
      e.onComplete();
    });
  }

  @Override
  public Observable<Boolean> removeChatById(long id) {
    return Observable.create(e -> {
      ChatDao dao = daoSession.getChatDao();
      Chat chat = dao.queryBuilder().where(ChatDao.Properties.Id.eq(id)).unique();
      if (chat != null){
        chat.setStatus(1);
        dao.update(chat);
        //check all chat removed
        List<Chat> chats = dao.
                queryBuilder().
                where(ChatDao.Properties.Id.eq(id),
                      ChatDao.Properties.Status.eq(0)).
                list();
        LogUtils.d(TAG, "chats size --> " + JsonUtils.get().toJson(chats));
        if (chats == null || chats.isEmpty()){
          updateGroupById(chat.getGroupId());
          e.onNext(Boolean.TRUE);
          e.onComplete();
        }
      }
    });
  }

  private void updateGroupById(long id){
    GroupDao dao = daoSession.getGroupDao();
    Group group = dao.
            queryBuilder().
            where(GroupDao.Properties.Id.eq(id)).
            unique();
    if (group != null){
      group.setStatus(1);
      dao.update(group);
    }
  }
}