package com.zdanovich.distributed_computing.model;


import jakarta.persistence.*;

@Entity
@Table(name="tbl_message")
public class Message {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;

    private String content;


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }


    public Issue getIssue() {
        return issue;
    }
    public void setIssue(Issue issue) {
        this.issue = issue;
    }


    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

}
