package com.zdanovich.distributed_computing.service;

import com.zdanovich.distributed_computing.dao.InMemoryIssueDao;
import com.zdanovich.distributed_computing.dto.request.IssueRequestTo;
import com.zdanovich.distributed_computing.dto.response.IssueResponseTo;
import com.zdanovich.distributed_computing.exception.EntityNotFoundException;
import com.zdanovich.distributed_computing.model.Issue;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IssueService {

    private final ModelMapper modelMapper;
    private final InMemoryIssueDao issueDao;

    @Autowired
    public IssueService(ModelMapper modelMapper, InMemoryIssueDao issueDao) {
        this.modelMapper = modelMapper;
        this.issueDao = issueDao;
    }

    public List<IssueResponseTo> findAll() {
        return issueDao.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public IssueResponseTo findById(long id) throws EntityNotFoundException {
        Issue issue = issueDao.findById(id).orElseThrow(() -> new EntityNotFoundException("This issue doesn't exist."));
        return convertToResponse(issue);
    }

    public IssueResponseTo save(IssueRequestTo issueRequestTo) {
        Issue issue = convertToIssue(issueRequestTo);
        issue.setCreated(new Date());
        issue.setModified(issue.getCreated());
        issueDao.save(issue);
        return convertToResponse(issue);
    }

    public IssueResponseTo update(IssueRequestTo issueRequestTo) throws EntityNotFoundException{
        Issue existingIssue = issueDao.findById(issueRequestTo.getId()).orElseThrow(() -> new EntityNotFoundException("This issue doesn't exist."));

        Issue updatedIssue = convertToIssue(issueRequestTo);
        updatedIssue.setId(issueRequestTo.getId());
        updatedIssue.setCreated(existingIssue.getCreated());
        updatedIssue.setModified(new Date());

        issueDao.save(updatedIssue);

        return convertToResponse(updatedIssue);
    }

    public IssueResponseTo partialUpdate(IssueRequestTo issueRequestTo) throws EntityNotFoundException{
        Issue issue = issueDao.findById(issueRequestTo.getId()).orElseThrow(() -> new EntityNotFoundException("This issue doesn't exist."));

        if (issueRequestTo.getContent() != null) {
            issue.setContent(issueRequestTo.getContent());
        }
        if (issueRequestTo.getTitle() != null) {
            issue.setTitle(issueRequestTo.getTitle());
        }
        if (issueRequestTo.getWriterId() != 0) {
            issue.setWriterId(issueRequestTo.getWriterId());
        }

        issue.setModified(new Date());
        issueDao.save(issue);

        return convertToResponse(issue);
    }

    public void delete(long id) throws EntityNotFoundException {
        issueDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Issue doesn't exist."));
        issueDao.deleteById(id);
    }

    private Issue convertToIssue(IssueRequestTo issueRequestTo) {
        return this.modelMapper.map(issueRequestTo, Issue.class);
    }

    private IssueResponseTo convertToResponse(Issue issue) {
        return this.modelMapper.map(issue, IssueResponseTo.class);
    }
}
