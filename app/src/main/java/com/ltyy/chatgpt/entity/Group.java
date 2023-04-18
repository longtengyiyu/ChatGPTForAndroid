package com.ltyy.chatgpt.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Group {
    @Id(autoincrement = true)
    private Long id;
    private String content;
    private long timestamp;
    private int status; //0:normal 1 deleted 2expired
    @Generated(hash = 1812956325)
    public Group(Long id, String content, long timestamp, int status) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
        this.status = status;
    }
    @Generated(hash = 117982048)
    public Group() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public long getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
