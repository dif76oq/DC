package com.zdanovich.distributed_computing.model;

import java.util.Date;

public class Issue {

    private long id;
    private long writerId;
    private String title;
    private String content;
    private Date created;
    private Date modified;


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }


    public long getWriterId() {
        return writerId;
    }
    public void setWriterId(long writerId) {
        this.writerId = writerId;
    }


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }


    public Date getModified() {
        return modified;
    }
    public void setModified(Date modified) {
        this.modified = modified;
    }



    public Issue(long writerId, String title, String content, Date created, Date modified) {
        this.writerId = writerId;
        this.title = title;
        this.content = content;
        this.created = created;
        this.modified = modified;
    }
}
