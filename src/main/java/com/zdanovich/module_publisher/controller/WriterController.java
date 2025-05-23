package com.zdanovich.module_publisher.controller;

import com.zdanovich.module_publisher.dto.request.WriterRequestTo;
import com.zdanovich.module_publisher.dto.response.WriterResponseTo;
import com.zdanovich.module_publisher.exception.DuplicateFieldException;
import com.zdanovich.module_publisher.exception.EntityNotFoundException;
import com.zdanovich.module_publisher.service.WriterService;
import com.zdanovich.module_publisher.validation.groups.OnCreateOrUpdate;
import com.zdanovich.module_publisher.validation.groups.OnPatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1.0/writers")
public class WriterController {

    private final WriterService writerService;

    @Autowired
    public WriterController(WriterService writerService) {
        this.writerService = writerService;
    }

    @GetMapping
    public ResponseEntity<List<WriterResponseTo>> findAll() {
        return new ResponseEntity<>(writerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        try {
            WriterResponseTo response = writerService.findById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-issue/{issueId}")
    public ResponseEntity<?> findByIssueId(@PathVariable long issueId) {
        try {
            WriterResponseTo response = writerService.findByIssueId(issueId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Validated(OnCreateOrUpdate.class) WriterRequestTo writerRequestTo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(writerService.save(writerRequestTo), HttpStatus.CREATED);
        } catch (DuplicateFieldException e) {
            return new ResponseEntity<>("{}", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Validated(OnCreateOrUpdate.class) WriterRequestTo writerRequestTo,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            WriterResponseTo updatedWriter = writerService.update(writerRequestTo);
            return new ResponseEntity<>(updatedWriter, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DuplicateFieldException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdate(@RequestBody @Validated(OnPatch.class) WriterRequestTo writerRequestTo,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            WriterResponseTo updatedWriter = writerService.partialUpdate(writerRequestTo);
            return new ResponseEntity<>(updatedWriter, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DuplicateFieldException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            writerService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
