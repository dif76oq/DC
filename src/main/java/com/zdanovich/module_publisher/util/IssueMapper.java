package com.zdanovich.module_publisher.util;

import com.zdanovich.module_publisher.dto.request.IssueRequestTo;
import com.zdanovich.module_publisher.dto.response.IssueResponseTo;
import com.zdanovich.module_publisher.model.Issue;

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
