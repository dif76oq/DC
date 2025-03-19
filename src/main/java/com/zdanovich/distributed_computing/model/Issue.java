package com.zdanovich.distributed_computing.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="tbl_issue")
public class Issue {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="writer_id")
    private Writer writer;

    private String title;
    private String content;
    private Date created;
    private Date modified;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "tbl_issue_mark",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "mark_id")
    )
    private Set<Mark> marks;

    @OneToMany(mappedBy = "issue")
    private List<Message> messages;


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }


    public Writer getWriter() {
        return writer;
    }
    public void setWriter(Writer writer) {
        this.writer = writer;
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


    public Set<Mark> getMarks() {
        return marks;
    }
    public void setMarks(Set<Mark> marks) {
        this.marks = marks;
    }
}
