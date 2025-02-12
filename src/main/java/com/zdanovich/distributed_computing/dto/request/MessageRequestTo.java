package com.zdanovich.distributed_computing.dto.request;

import com.zdanovich.distributed_computing.validation.groups.OnCreateOrUpdate;
import com.zdanovich.distributed_computing.validation.groups.OnPatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MessageRequestTo {

    @NotBlank(message="Issue ID can't be empty", groups = OnCreateOrUpdate.class)
    private long issueId;

    @Size(min = 2, max = 2048, message = "Content size must be between 2..64 characters", groups = {OnPatch.class, OnCreateOrUpdate.class})
    @NotBlank(message="Content can't be empty", groups = OnCreateOrUpdate.class)
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
