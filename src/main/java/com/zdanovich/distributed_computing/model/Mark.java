package com.zdanovich.distributed_computing.model;

import jakarta.validation.constraints.Size;

public class Mark {

    private long id;

    @Size(min = 2, max = 32, message = "Name size must be between 2..64 characters")
    private String name;

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


    public Mark(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
