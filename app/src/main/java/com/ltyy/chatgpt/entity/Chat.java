package com.ltyy.chatgpt.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Chat {

    public static final int TYPE_AI = 0;
    public static final int TYPE_USER = 1;
    public static final int TYPE_TIPS = 2;
    public static final int TYPE_INPUT = 3;

    private Long groupId;
    @Id(autoincrement = true)
    private Long id;
    private String content="";
    private int type;
    private String name;
    private int status; //0:normal 1:deleted 2:expired

    @Generated(hash = 782219211)
    public Chat(Long groupId, Long id, String content, int type, String name,
            int status) {
        this.groupId = groupId;
        this.id = id;
        this.content = content;
        this.type = type;
        this.name = name;
        this.status = status;
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

    public boolean isInput(){
        return type == TYPE_INPUT;
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

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
