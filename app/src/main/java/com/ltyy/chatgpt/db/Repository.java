package com.ltyy.chatgpt.db;


import com.ltyy.chatgpt.entity.Chat;

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
//  private DaoSession daoSession;

  private Repository(){
//    daoSession = ChatDaoHelper.instance().getDaoSession();
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
  public Observable<Integer> getChatGroupId() {
    return null;
  }

  @Override
  public Observable<Boolean> saveChat(Chat chat) {
    return null;
  }

  @Override
  public Observable<List<Chat>> getChatListByGroupId(String name) {
    return null;
  }
}