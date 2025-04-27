package com.zdanovich.module_publisher.util;

import org.modelmapper.ModelMapper;

public interface CustomEntityMapper<Request, Entity, Response> {

    ModelMapper modelMapper = new ModelMapper();

    Entity convertToModel(Request requestDTO);
    Response convertToResponse(Entity entity);
}

