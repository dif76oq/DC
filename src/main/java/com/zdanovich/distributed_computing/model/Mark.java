package com.zdanovich.distributed_computing.model;

public class Mark {

    private long id;
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
