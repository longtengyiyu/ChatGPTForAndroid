package com.ltyy.chatgpt.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.ltyy.chatgpt.entity.Chat;

import com.ltyy.chatgpt.greendao.ChatDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig chatDaoConfig;

    private final ChatDao chatDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        chatDaoConfig = daoConfigMap.get(ChatDao.class).clone();
        chatDaoConfig.initIdentityScope(type);

        chatDao = new ChatDao(chatDaoConfig, this);

        registerDao(Chat.class, chatDao);
    }
    
    public void clear() {
        chatDaoConfig.clearIdentityScope();
    }

    public ChatDao getChatDao() {
        return chatDao;
    }

}