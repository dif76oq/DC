package com.zdanovich.distributed_computing.dto.request;

import jakarta.validation.constraints.Size;

public class MarkRequestTo {

    @Size(min = 2, max = 32, message = "Name size must be between 2..64 characters")
    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
