package com.zdanovich.distributed_computing.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class MessageRequestTo {

    private long issueId;

    @Size(min = 2, max = 2048, message = "Content size must be between 2..64 characters")
    @NotEmpty(message="Content can't be empty")
    private String content;

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
}
