package com.zdanovich.distributed_computing.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Message {

    private long id;

    private long issueId;

    @Size(min = 2, max = 2048, message = "Content size must be between 2..64 characters")
    @NotEmpty(message="Content can't be empty")
    private String content;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }


    public long getIssueId() {
        return issueId;
    }
    public void setIssueId(long issueId) {
        this.issueId = issueId;
    }


    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }


    public Message(long id, long issueId, String content) {
        this.id = id;
        this.issueId = issueId;
        this.content = content;
    }
}
