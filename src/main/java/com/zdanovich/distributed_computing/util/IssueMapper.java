package com.zdanovich.distributed_computing.util;

import com.zdanovich.distributed_computing.dto.request.IssueRequestTo;
import com.zdanovich.distributed_computing.dto.response.IssueResponseTo;
import com.zdanovich.distributed_computing.model.Issue;

public class IssueMapper implements CustomEntityMapper<IssueRequestTo, Issue, IssueResponseTo> {

    @Override
    public Issue convertToModel(IssueRequestTo requestDTO) {
        return null;
    }

    @Override
    public IssueResponseTo convertToResponse(Issue issue) {
        return modelMapper.map(issue, IssueResponseTo.class);
    }
}
