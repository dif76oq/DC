package com.zdanovich.distributed_computing.dto.request;

import com.zdanovich.distributed_computing.validation.groups.OnCreateOrUpdate;
import com.zdanovich.distributed_computing.validation.groups.OnPatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

public class IssueRequestTo {

    private long id;

    private long writerId;

    @Size(min = 2, max = 64, message = "Title size must be between 2..64 characters", groups = {OnPatch.class, OnCreateOrUpdate.class})
    @NotBlank(message="Title can't be empty", groups = OnCreateOrUpdate.class)
    private String title;

    @Size(min = 4, max = 2048, message = "Content size must be between 2..64 characters", groups = {OnPatch.class, OnCreateOrUpdate.class})
    @NotBlank(message="Content can't be empty", groups = OnCreateOrUpdate.class)
    private String content;

    private Set<String> marks = new HashSet<>();


    public long getWriterId() {return writerId;}
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
