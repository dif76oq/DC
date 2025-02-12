package com.zdanovich.distributed_computing.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Writer {

    private long id;

    @NotBlank(message = "Email can't be empty")
    @Email(message = "Email should be in format username@example.com")
    @Size(min = 2, max = 64, message = "Login size must be between 2..64 characters")
    private String login;

    @NotEmpty(message="Password can't be empty")
    @Size(min = 8, max = 128, message = "Password must be between 8..128 characters")
    private String password;

    @Size(min = 2, max = 64, message = "Your firstname must be between 2..64 characters")
    private String firstname;

    @Size(min = 2, max = 64, message = "Your lastname must be between 2..64 characters")
    private String lastname;

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
