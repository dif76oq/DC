package com.zdanovich.distributed_computing.service;

import com.zdanovich.distributed_computing.dao.InMemoryIssueDao;
import com.zdanovich.distributed_computing.dao.InMemoryWriterDao;
import com.zdanovich.distributed_computing.dto.request.WriterRequestTo;
import com.zdanovich.distributed_computing.dto.response.WriterResponseTo;
import com.zdanovich.distributed_computing.exception.DuplicateFieldException;
import com.zdanovich.distributed_computing.exception.EntityNotFoundException;
import com.zdanovich.distributed_computing.model.Issue;
import com.zdanovich.distributed_computing.model.Writer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WriterService {

    private final ModelMapper modelMapper;
    private final InMemoryWriterDao writerDao;
    private final InMemoryIssueDao issueDao;

    @Autowired
    public WriterService(ModelMapper modelMapper, InMemoryWriterDao writerDao, InMemoryIssueDao issueDao) {
        this.modelMapper = modelMapper;
        this.writerDao = writerDao;
        this.issueDao = issueDao;
    }

    public List<WriterResponseTo> findAll() {
        return writerDao.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public WriterResponseTo findById(long id) throws EntityNotFoundException {
        Writer writer = writerDao.findById(id).orElseThrow(() -> new EntityNotFoundException("This writer doesn't exist."));

        return convertToResponse(writer);
    }

    public WriterResponseTo findByIssueId(long issueId) throws EntityNotFoundException {
        Issue issue = issueDao.findById(issueId).orElseThrow(() -> new EntityNotFoundException("Issue with id " + issueId + " doesn't exist."));
        Writer writer = writerDao.findById(issue.getWriterId()).orElseThrow(() -> new EntityNotFoundException("No writer found with issue id " + issueId));
        return convertToResponse(writer);
    }

    public WriterResponseTo save(WriterRequestTo writerRequestTo) throws DuplicateFieldException {

        if (writerDao.findByLogin(writerRequestTo.getLogin()).isPresent()) {
            throw new DuplicateFieldException("login", writerRequestTo.getLogin());
        }

        Writer writer = convertToWriter(writerRequestTo);
        writerDao.save(writer);
        return convertToResponse(writer);
    }

    public WriterResponseTo update(WriterRequestTo writerRequestTo) throws EntityNotFoundException, DuplicateFieldException {
        writerDao.findById(writerRequestTo.getId()).orElseThrow(() -> new EntityNotFoundException("This writer doesn't exist."));

        writerDao.findByLogin(writerRequestTo.getLogin())
                .filter(writer -> writer.getId() != writerRequestTo.getId())
                .ifPresent(writer -> {
                    throw new DuplicateFieldException("login", writerRequestTo.getLogin());
                });

        Writer updatedWriter = convertToWriter(writerRequestTo);
        writerDao.save(updatedWriter);
        return convertToResponse(updatedWriter);
    }

    public WriterResponseTo partialUpdate(WriterRequestTo writerRequestTo) throws EntityNotFoundException, DuplicateFieldException {

        Writer writer = writerDao.findById(writerRequestTo.getId()).orElseThrow(() -> new EntityNotFoundException("Writer not found"));

        if (writerRequestTo.getLogin() != null && !writerRequestTo.getLogin().equals(writer.getLogin())) {
            if (writerDao.findByLogin(writerRequestTo.getLogin()).isPresent()) {
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

        writerDao.save(writer);

        return convertToResponse(writer);
    }

    public void delete(long id) throws EntityNotFoundException {
        writerDao.findById(id).orElseThrow(() -> new EntityNotFoundException("This writer doesn't exist."));
        writerDao.deleteById(id);
    }


    private Writer convertToWriter(WriterRequestTo writerRequestTo) {
        return this.modelMapper.map(writerRequestTo, Writer.class);
    }
    private WriterResponseTo convertToResponse(Writer writer) {
        return this.modelMapper.map(writer, WriterResponseTo.class);
    }
}
