package com.zdanovich.distributed_computing.service;

import com.zdanovich.distributed_computing.dao.InMemoryWriterDao;
import com.zdanovich.distributed_computing.dto.request.WriterRequestTo;
import com.zdanovich.distributed_computing.dto.response.WriterResponseTo;
import com.zdanovich.distributed_computing.exception.EntityNotFoundException;
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

    @Autowired
    public WriterService(ModelMapper modelMapper, InMemoryWriterDao writerDao) {
        this.modelMapper = modelMapper;
        this.writerDao = writerDao;
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

    public WriterResponseTo save(WriterRequestTo writerRequestTo) {
        Writer writer = convertToWriter(writerRequestTo);
        writerDao.save(writer);
        return convertToResponse(writer);
    }

    public WriterResponseTo update(long id, WriterRequestTo writerRequestTo) throws EntityNotFoundException {
        writerDao.findById(id).orElseThrow(() -> new EntityNotFoundException("This writer doesn't exist."));

        Writer updatedWriter = convertToWriter(writerRequestTo);
        updatedWriter.setId(id);
        writerDao.save(updatedWriter);

        return convertToResponse(updatedWriter);
    }

    public WriterResponseTo partialUpdate(long id, WriterRequestTo writerRequestTo) throws EntityNotFoundException {

        Writer writer = writerDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Writer not found"));

        if (writerRequestTo.getFirstname() != null) {
            writer.setFirstname(writerRequestTo.getFirstname());
        }
        if (writerRequestTo.getLastname() != null) {
            writer.setLastname(writerRequestTo.getLastname());
        }
        if (writerRequestTo.getLogin() != null) {
            writer.setLogin(writerRequestTo.getLogin());
        }
        if (writerRequestTo.getPassword() != null) {
            writer.setPassword(writerRequestTo.getPassword());
        }

        writerDao.save(writer);

        return convertToResponse(writer);
    }

    public void delete(long id) {
        writerDao.deleteById(id);
    }


    private Writer convertToWriter(WriterRequestTo writerRequestTo) {
        return this.modelMapper.map(writerRequestTo, Writer.class);
    }
    private WriterResponseTo convertToResponse(Writer writer) {
        return this.modelMapper.map(writer, WriterResponseTo.class);
    }
}
