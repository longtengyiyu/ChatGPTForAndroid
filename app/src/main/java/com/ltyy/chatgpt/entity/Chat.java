package com.ltyy.chatgpt.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Chat {

    public static final int TYPE_AI = 0;
    public static final int TYPE_USER = 1;
    public static final int TYPE_TIPS = 2;
    private Long groupId;
    @Id(autoincrement = true)
    private Long id;
    private String content;
    private int type;
    private String name;

    @Generated(hash = 1013681761)
    public Chat(Long groupId, Long id, String content, int type, String name) {
        this.groupId = groupId;
        this.id = id;
        this.content = content;
        this.type = type;
        this.name = name;
    }

    @Generated(hash = 519536279)
    public Chat() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLeftMessage(){
        return type == TYPE_AI;
    }

    public boolean isRightMessage(){
        return type == TYPE_USER;
    }

    public Long getGroupId() {
        return this.groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}