package com.zdanovich.distributed_computing.dto.request;

import com.zdanovich.distributed_computing.model.Issue;
import com.zdanovich.distributed_computing.validation.groups.OnCreateOrUpdate;
import com.zdanovich.distributed_computing.validation.groups.OnPatch;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class MarkRequestTo {

    private long id;
    @Size(min = 2, max = 32, message = "Name size must be between 2..64 characters", groups = {OnPatch.class, OnCreateOrUpdate.class})
    private String name;

    private Set<Issue> issues;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public Set<Issue> getIssues() {
        return issues;
    }

    public void setIssues(Set<Issue> issues) {
        this.issues = issues;
    }
}
