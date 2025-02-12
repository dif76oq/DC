package com.zdanovich.distributed_computing.controller;

import com.zdanovich.distributed_computing.dto.request.IssueRequestTo;
import com.zdanovich.distributed_computing.dto.response.IssueResponseTo;
import com.zdanovich.distributed_computing.exception.EntityNotFoundException;
import com.zdanovich.distributed_computing.service.IssueService;
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
@RequestMapping("/api/v1.0/issues")
public class IssueController {

    private final IssueService issueService;

    @Autowired
    public IssueController(IssueService IssueService) {
        this.issueService = IssueService;
    }


    @GetMapping("")
    public ResponseEntity<List<IssueResponseTo>> findAll() {
        return new ResponseEntity<>(issueService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        try {
            IssueResponseTo response = issueService.findById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> save(@RequestBody @Validated(OnCreateOrUpdate.class) IssueRequestTo issueRequestTo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(issueService.save(issueRequestTo), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id,
                                    @RequestBody @Validated(OnCreateOrUpdate.class) IssueRequestTo issueRequestTo,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            IssueResponseTo updatedIssue = issueService.update(id, issueRequestTo);
            return new ResponseEntity<>(updatedIssue, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdate(@PathVariable long id,
                                           @RequestBody @Validated(OnPatch.class) IssueRequestTo issueRequestTo,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            IssueResponseTo updatedIssue = issueService.partialUpdate(id, issueRequestTo);
            return new ResponseEntity<>(updatedIssue, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        issueService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
