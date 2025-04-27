package com.zdanovich.module_publisher.util;

import com.zdanovich.module_publisher.dto.request.WriterRequestTo;
import com.zdanovich.module_publisher.dto.response.WriterResponseTo;
import com.zdanovich.module_publisher.model.Writer;

public class WriterMapper implements CustomEntityMapper<WriterRequestTo, Writer, WriterResponseTo> {
    @Override
    public Writer convertToModel(WriterRequestTo requestDTO) {
        return null;
    }

    @Override
    public WriterResponseTo convertToResponse(Writer writer) {
        return null;
    }
}
