package com.zdanovich.distributed_computing.util;

import com.zdanovich.distributed_computing.dto.request.WriterRequestTo;
import com.zdanovich.distributed_computing.dto.response.WriterResponseTo;
import com.zdanovich.distributed_computing.model.Writer;

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
