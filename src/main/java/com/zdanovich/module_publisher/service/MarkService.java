package com.zdanovich.module_publisher.service;

import com.zdanovich.module_publisher.dto.request.MarkRequestTo;
import com.zdanovich.module_publisher.dto.response.MarkResponseTo;
import com.zdanovich.module_publisher.exception.DuplicateFieldException;
import com.zdanovich.module_publisher.exception.EntityNotFoundException;
import com.zdanovich.module_publisher.model.Mark;
import com.zdanovich.module_publisher.repository.MarkRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarkService {

    private final ModelMapper modelMapper;
    private final MarkRepository markRepository;

    @Autowired
    public MarkService(ModelMapper modelMapper, MarkRepository markRepository) {
        this.modelMapper = modelMapper;
        this.markRepository = markRepository;
    }

    public List<MarkResponseTo> findAll() {
        return markRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public MarkResponseTo findById(long id) throws EntityNotFoundException {
        Mark mark = markRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("This mark doesn't exist."));

        return convertToResponse(mark);
    }

    public MarkResponseTo save(MarkRequestTo markRequestTo) throws DuplicateFieldException {
        if (markRepository.findByName(markRequestTo.getName()).isPresent()) {
            throw new DuplicateFieldException("name", markRequestTo.getName());
        }

        Mark mark = convertToMark(markRequestTo);
        markRepository.save(mark);
        return convertToResponse(mark);
    }

    public MarkResponseTo update(MarkRequestTo markRequestTo) throws EntityNotFoundException, DuplicateFieldException {
        markRepository.findById(markRequestTo.getId()).orElseThrow(() -> new EntityNotFoundException("This mark doesn't exist."));

        markRepository.findByName(markRequestTo.getName())
                .filter(mark -> mark.getId() != markRequestTo.getId())
                .ifPresent(mark -> {
                    throw new DuplicateFieldException("name", markRequestTo.getName());
                });

        Mark updatedMark = convertToMark(markRequestTo);
        markRepository.save(updatedMark);

        return convertToResponse(updatedMark);
    }

    public void delete(long id) {
        markRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Mark doesn't exist."));
        markRepository.deleteById(id);
    }

    private Mark convertToMark(MarkRequestTo markRequestTo) {
        return this.modelMapper.map(markRequestTo, Mark.class);
    }

    public MarkResponseTo convertToResponse(Mark mark) {
        return this.modelMapper.map(mark, MarkResponseTo.class);
    }
}
