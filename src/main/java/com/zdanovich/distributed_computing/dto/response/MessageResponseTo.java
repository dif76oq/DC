package com.zdanovich.distributed_computing.dto.response;

public class MessageResponseTo {

    private long issueId;

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
