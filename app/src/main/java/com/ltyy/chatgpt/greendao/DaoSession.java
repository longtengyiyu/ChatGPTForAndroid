package com.ltyy.chatgpt.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.ltyy.chatgpt.entity.Chat;
import com.ltyy.chatgpt.entity.Group;

import com.ltyy.chatgpt.greendao.ChatDao;
import com.ltyy.chatgpt.greendao.GroupDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig chatDaoConfig;
    private final DaoConfig groupDaoConfig;

    private final ChatDao chatDao;
    private final GroupDao groupDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        chatDaoConfig = daoConfigMap.get(ChatDao.class).clone();
        chatDaoConfig.initIdentityScope(type);

        groupDaoConfig = daoConfigMap.get(GroupDao.class).clone();
        groupDaoConfig.initIdentityScope(type);

        chatDao = new ChatDao(chatDaoConfig, this);
        groupDao = new GroupDao(groupDaoConfig, this);

        registerDao(Chat.class, chatDao);
        registerDao(Group.class, groupDao);
    }
    
    public void clear() {
        chatDaoConfig.clearIdentityScope();
        groupDaoConfig.clearIdentityScope();
    }

    public ChatDao getChatDao() {
        return chatDao;
    }

    public GroupDao getGroupDao() {
        return groupDao;
    }

}
