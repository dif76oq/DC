package com.zdanovich.distributed_computing.controller;

import com.zdanovich.distributed_computing.dto.request.WriterRequestTo;
import com.zdanovich.distributed_computing.dto.response.WriterResponseTo;
import com.zdanovich.distributed_computing.exception.EntityNotFoundException;
import com.zdanovich.distributed_computing.service.WriterService;
import com.zdanovich.distributed_computing.validation.groups.OnCreateOrUpdate;
import com.zdanovich.distributed_computing.validation.groups.OnPatch;
import jakarta.validation.Valid;
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

    @GetMapping("")
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

    @PostMapping("/create")
    public ResponseEntity<?> save(@RequestBody @Validated(OnCreateOrUpdate.class) WriterRequestTo writerRequestTo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(writerService.save(writerRequestTo), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id,
                                    @RequestBody @Validated(OnCreateOrUpdate.class) WriterRequestTo writerRequestTo,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            WriterResponseTo updatedWriter = writerService.update(id, writerRequestTo);
            return new ResponseEntity<>(updatedWriter, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdate(@PathVariable long id,
                                           @RequestBody @Validated(OnPatch.class) WriterRequestTo writerRequestTo,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            WriterResponseTo updatedWriter = writerService.partialUpdate(id, writerRequestTo);
            return new ResponseEntity<>(updatedWriter, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        writerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
