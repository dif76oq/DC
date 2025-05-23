package com.zdanovich.module_publisher.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="tbl_writer")
public class Writer {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String login;
    private String password;
    private String firstname;
    private String lastname;

    @OneToMany(mappedBy = "writer")
    private List<Issue> issues;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }


    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }


    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }


    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

}
