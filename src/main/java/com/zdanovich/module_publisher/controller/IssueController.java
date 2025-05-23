package com.zdanovich.module_publisher.controller;

import com.zdanovich.module_publisher.dto.request.IssueRequestTo;
import com.zdanovich.module_publisher.dto.response.IssueResponseTo;
import com.zdanovich.module_publisher.dto.response.MessageResponseTo;
import com.zdanovich.module_publisher.exception.DuplicateFieldException;
import com.zdanovich.module_publisher.exception.EntityNotFoundException;
import com.zdanovich.module_publisher.service.IssueService;
import com.zdanovich.module_publisher.service.MessageService;
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
@RequestMapping("/api/v1.0/issues")
public class IssueController {

    private final IssueService issueService;
    private final MessageService messageService;

    @Autowired
    public IssueController(IssueService IssueService, MessageService messageService) {
        this.issueService = IssueService;
        this.messageService = messageService;
    }


    @GetMapping
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

    @GetMapping("/{id}/messages")
    public ResponseEntity<List<MessageResponseTo>> findMessagesByIssueId(@PathVariable long id) {

        List<MessageResponseTo> messages = messageService.findByIssueId(id);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

//    @GetMapping("/{id}/marks")
//    public ResponseEntity<?> findMarksByIssueId(@PathVariable long id) {
//        return new ResponseEntity<>(issueService.findMarksByIssueId(id), HttpStatus.OK);
//    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Validated(OnCreateOrUpdate.class) IssueRequestTo issueRequestTo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(issueService.save(issueRequestTo), HttpStatus.CREATED);
        } catch (DuplicateFieldException e) {
            return new ResponseEntity<>("{}", HttpStatus.FORBIDDEN);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("{}", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Validated(OnCreateOrUpdate.class) IssueRequestTo issueRequestTo,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            IssueResponseTo updatedIssue = issueService.update(issueRequestTo);
            return new ResponseEntity<>(updatedIssue, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping
    public ResponseEntity<?> partialUpdate(@RequestBody @Validated(OnPatch.class) IssueRequestTo issueRequestTo,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            IssueResponseTo updatedIssue = issueService.partialUpdate(issueRequestTo);
            return new ResponseEntity<>(updatedIssue, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            issueService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
