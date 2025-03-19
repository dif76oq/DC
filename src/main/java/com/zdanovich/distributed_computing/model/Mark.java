package com.zdanovich.distributed_computing.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="tbl_mark")
public class Mark {

    public Mark() {
    }
    public Mark(String name) {
        this.name = name;
    }

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToMany(mappedBy = "marks")
    private Set<Issue> issues;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Set<Issue> getIssues() {
        return issues;
    }
    public void setIssues(Set<Issue> issues) {
        this.issues = issues;
    }

}
