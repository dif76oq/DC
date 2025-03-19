package com.zdanovich.distributed_computing.service;

import com.zdanovich.distributed_computing.dto.request.WriterRequestTo;
import com.zdanovich.distributed_computing.dto.response.WriterResponseTo;
import com.zdanovich.distributed_computing.exception.DuplicateFieldException;
import com.zdanovich.distributed_computing.exception.EntityNotFoundException;
import com.zdanovich.distributed_computing.model.Issue;
import com.zdanovich.distributed_computing.model.Writer;
import com.zdanovich.distributed_computing.repository.IssueRepository;
import com.zdanovich.distributed_computing.repository.MarkRepository;
import com.zdanovich.distributed_computing.repository.WriterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WriterService {

    private final ModelMapper modelMapper;
    private final IssueRepository issueRepository;
    private final WriterRepository writerRepository;

    @Autowired
    public WriterService(ModelMapper modelMapper, IssueRepository issueRepository, WriterRepository writerRepository) {
        this.modelMapper = modelMapper;
        this.issueRepository = issueRepository;
        this.writerRepository = writerRepository;
    }

    public List<WriterResponseTo> findAll() {
        return writerRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public WriterResponseTo findById(long id) throws EntityNotFoundException {
        Writer writer = writerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("This writer doesn't exist."));

        return convertToResponse(writer);
    }

    public WriterResponseTo findByIssueId(long issueId) throws EntityNotFoundException {
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new EntityNotFoundException("Issue with id " + issueId + " doesn't exist."));
        Writer writer = writerRepository.findById(issue.getWriter().getId()).orElseThrow(() -> new EntityNotFoundException("No writer found with issue id " + issueId));
        return convertToResponse(writer);
    }

    public WriterResponseTo save(WriterRequestTo writerRequestTo) throws DuplicateFieldException {

        if (writerRepository.findByLogin(writerRequestTo.getLogin()).isPresent()) {
            throw new DuplicateFieldException("login", writerRequestTo.getLogin());
        }

        Writer writer = convertToWriter(writerRequestTo);
        writerRepository.save(writer);
        return convertToResponse(writer);
    }

    public WriterResponseTo update(WriterRequestTo writerRequestTo) throws EntityNotFoundException, DuplicateFieldException {
        writerRepository.findById(writerRequestTo.getId()).orElseThrow(() -> new EntityNotFoundException("This writer doesn't exist."));

        writerRepository.findByLogin(writerRequestTo.getLogin())
                .filter(writer -> writer.getId() != writerRequestTo.getId())
                .ifPresent(writer -> {
                    throw new DuplicateFieldException("login", writerRequestTo.getLogin());
                });

        Writer updatedWriter = convertToWriter(writerRequestTo);
        writerRepository.save(updatedWriter);
        return convertToResponse(updatedWriter);
    }

    public WriterResponseTo partialUpdate(WriterRequestTo writerRequestTo) throws EntityNotFoundException, DuplicateFieldException {

        Writer writer = writerRepository.findById(writerRequestTo.getId()).orElseThrow(() -> new EntityNotFoundException("Writer not found"));

        if (writerRequestTo.getLogin() != null && !writerRequestTo.getLogin().equals(writer.getLogin())) {
            if (writerRepository.findByLogin(writerRequestTo.getLogin()).isPresent()) {
                throw new DuplicateFieldException("login", writerRequestTo.getLogin());
            }
            writer.setLogin(writerRequestTo.getLogin());
        }
        if (writerRequestTo.getFirstname() != null) {
            writer.setFirstname(writerRequestTo.getFirstname());
        }
        if (writerRequestTo.getLastname() != null) {
            writer.setLastname(writerRequestTo.getLastname());
        }
        if (writerRequestTo.getPassword() != null) {
            writer.setPassword(writerRequestTo.getPassword());
        }

        writerRepository.save(writer);

        return convertToResponse(writer);
    }

    public void delete(long id) throws EntityNotFoundException {
        writerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("This writer doesn't exist."));
        writerRepository.deleteById(id);
    }


    private Writer convertToWriter(WriterRequestTo writerRequestTo) {
        return this.modelMapper.map(writerRequestTo, Writer.class);
    }
    public WriterResponseTo convertToResponse(Writer writer) {
        return this.modelMapper.map(writer, WriterResponseTo.class);
    }
}
