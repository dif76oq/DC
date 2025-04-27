package com.zdanovich.module_publisher.service;

import com.zdanovich.module_publisher.dto.request.IssueRequestTo;
import com.zdanovich.module_publisher.dto.response.IssueResponseTo;
import com.zdanovich.module_publisher.exception.DuplicateFieldException;
import com.zdanovich.module_publisher.exception.EntityNotFoundException;
import com.zdanovich.module_publisher.model.Issue;
import com.zdanovich.module_publisher.model.Mark;
import com.zdanovich.module_publisher.model.Writer;
import com.zdanovich.module_publisher.repository.IssueRepository;
import com.zdanovich.module_publisher.repository.MarkRepository;
import com.zdanovich.module_publisher.repository.WriterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class IssueService {

    private final ModelMapper modelMapper;
    private final IssueRepository issueRepository;
    private final WriterRepository writerRepository;
    private final MarkRepository markRepository;

    @Autowired
    public IssueService(ModelMapper modelMapper, IssueRepository issueRepository, WriterRepository writerRepository, MarkRepository markRepository) {
        this.modelMapper = modelMapper;
        this.issueRepository = issueRepository;
        this.writerRepository = writerRepository;
        this.markRepository = markRepository;
    }

    public List<IssueResponseTo> findAll() {
        List<Issue> issues = issueRepository.findAll();
        return issues.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    public IssueResponseTo findById(long id) throws EntityNotFoundException {
        Issue issue = issueRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("This issue doesn't exist."));
        return convertToResponse(issue);
    }

//    public List<MarkResponseTo> findMarksByIssueId(long id) {
//        return findById(id).getMarks().stream()
//                .map(mark -> markService.convertToResponse(mark))
//                .collect(Collectors.toList());
//    }



    public IssueResponseTo save(IssueRequestTo issueRequestTo) throws DuplicateFieldException, EntityNotFoundException {

        if (issueRepository.findByTitle(issueRequestTo.getTitle()).isPresent()) {
            throw new DuplicateFieldException("title", issueRequestTo.getTitle());
        }

        Issue issue = convertToIssue(issueRequestTo);
        issue.setCreated(new Date());
        issue.setModified(issue.getCreated());
        issue = issueRepository.save(issue);

        return convertToResponse(issue);
    }

    public IssueResponseTo update(IssueRequestTo issueRequestTo) throws EntityNotFoundException{
        Issue existingIssue = issueRepository.findById(issueRequestTo.getId()).orElseThrow(() -> new EntityNotFoundException("This issue doesn't exist."));


        issueRepository.findByTitle(issueRequestTo.getTitle())
                .filter(issue -> issue.getId() != issueRequestTo.getId())
                .ifPresent(issue -> {
                    throw new DuplicateFieldException("title", issueRequestTo.getTitle());
                });

        Issue updatedIssue = convertToIssue(issueRequestTo);
        updatedIssue.setId(issueRequestTo.getId());
        updatedIssue.setCreated(existingIssue.getCreated());
        updatedIssue.setModified(new Date());

        issueRepository.save(updatedIssue);

        return convertToResponse(updatedIssue);
    }

    public IssueResponseTo partialUpdate(IssueRequestTo issueRequestTo) throws EntityNotFoundException{
        Issue issue = issueRepository.findById(issueRequestTo.getId()).orElseThrow(() -> new EntityNotFoundException("This issue doesn't exist."));

        if (issueRequestTo.getTitle() != null && !issueRequestTo.getTitle().equals(issue.getTitle())) {
            if (issueRepository.findByTitle(issueRequestTo.getTitle()).isPresent()) {
                throw new DuplicateFieldException("title", issueRequestTo.getTitle());
            }
            issue.setTitle(issueRequestTo.getTitle());
        }

        if (issueRequestTo.getContent() != null) {
            issue.setContent(issueRequestTo.getContent());
        }

        if (issueRequestTo.getWriterId() != 0) {
            Writer writer = writerRepository.findById(issueRequestTo.getWriterId()).get();
            issue.setWriter(writer);
        }

        issue.setModified(new Date());
        issueRepository.save(issue);

        return convertToResponse(issue);
    }

    public void delete(long id) throws EntityNotFoundException {
        issueRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Issue doesn't exist."));
        issueRepository.deleteById(id);
    }

    private Issue convertToIssue(IssueRequestTo issueRequestTo) throws EntityNotFoundException {
        Issue issue = modelMapper.map(issueRequestTo, Issue.class);

        if (issueRequestTo.getWriterId() != 0) {
            Writer writer = writerRepository.findById(issueRequestTo.getWriterId())
                    .orElseThrow(() -> new EntityNotFoundException("Writer not found with id: " + issueRequestTo.getWriterId()));
            issue.setWriter(writer);
        }

        if (issueRequestTo.getMarks() != null && !issueRequestTo.getMarks().isEmpty()) {
            Set<Mark> marks = issueRequestTo.getMarks().stream()
                    .map(name -> markRepository.findByName(name)
                            .orElseGet(() -> new Mark(name)))
                    .collect(Collectors.toSet());

            issue.setMarks(marks);
        }

        return issue;
    }

    public IssueResponseTo convertToResponse(Issue issue) {
        return this.modelMapper.map(issue, IssueResponseTo.class);
    }
}
