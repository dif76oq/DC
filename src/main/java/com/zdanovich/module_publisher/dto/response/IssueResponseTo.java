package com.zdanovich.module_publisher.dto.response;

import java.util.Date;
import java.util.Set;

public class IssueResponseTo {

    private long id;
    private long writerId;
    private String title;
    private String content;
    private Date created;
    private Date modified;
    private Set<String> marks;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<String> getMarks() {
        return marks;
    }

    public void setMarks(Set<String> marks) {
        this.marks = marks;
    }
}
