package com.zdanovich.distributed_computing.service;

import com.zdanovich.distributed_computing.dto.request.IssueRequestTo;
import com.zdanovich.distributed_computing.dto.request.MessageRequestTo;

import com.zdanovich.distributed_computing.dto.response.MessageResponseTo;
import com.zdanovich.distributed_computing.exception.EntityNotFoundException;
import com.zdanovich.distributed_computing.model.Issue;
import com.zdanovich.distributed_computing.model.Message;

import com.zdanovich.distributed_computing.model.Writer;
import com.zdanovich.distributed_computing.repository.IssueRepository;
import com.zdanovich.distributed_computing.repository.MessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final ModelMapper modelMapper;
    private final MessageRepository messageRepository;
    private final IssueRepository issueRepository;

    @Autowired
    public MessageService(ModelMapper modelMapper, MessageRepository messageRepository, IssueRepository issueRepository) {
        this.modelMapper = modelMapper;
        this.messageRepository = messageRepository;
        this.issueRepository = issueRepository;
    }


    public List<MessageResponseTo> findAll() {
        return messageRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public MessageResponseTo findById(long id) throws EntityNotFoundException {
        Message message = messageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("This message doesn't exist."));

        return convertToResponse(message);
    }

    public List<MessageResponseTo> findByIssueId(long issueId) {
        return messageRepository.findByIssueId(issueId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public MessageResponseTo save(MessageRequestTo messageRequestTo) throws EntityNotFoundException{
        Message message = convertToMessage(messageRequestTo);
        messageRepository.save(message);
        return convertToResponse(message);
    }

    public MessageResponseTo update(MessageRequestTo messageRequestTo) throws EntityNotFoundException {
        messageRepository.findById(messageRequestTo.getId()).orElseThrow(() -> new EntityNotFoundException("This message doesn't exist."));

        Message updatedMessage = convertToMessage(messageRequestTo);
        updatedMessage.setId(messageRequestTo.getId());
        messageRepository.save(updatedMessage);

        return convertToResponse(updatedMessage);
    }

    public MessageResponseTo partialUpdate(MessageRequestTo messageRequestTo) throws EntityNotFoundException {

        Message message = messageRepository.findById(messageRequestTo.getId()).orElseThrow(() -> new EntityNotFoundException("Message not found"));

        if (messageRequestTo.getContent() != null) {
            message.setContent(messageRequestTo.getContent());
        }
        if (messageRequestTo.getIssueId() != 0) {
            Issue issue = issueRepository.findById(messageRequestTo.getIssueId()).get();
            message.setIssue(issue);
        }

        messageRepository.save(message);

        return convertToResponse(message);
    }

    public void delete(long id) throws EntityNotFoundException {
        messageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Message doesn't exist."));
        messageRepository.deleteById(id);
    }

    private Message convertToMessage(MessageRequestTo messageRequestTo) throws EntityNotFoundException{
        Message message = modelMapper.map(messageRequestTo, Message.class);
        if (messageRequestTo.getIssueId() != 0) {
            Issue issue = issueRepository.findById(messageRequestTo.getIssueId())
                    .orElseThrow(() -> new EntityNotFoundException("Writer not found with id: " + messageRequestTo.getIssueId()));
            message.setIssue(issue);
        }

        return message;
    }


    public MessageResponseTo convertToResponse(Message message) {
        return this.modelMapper.map(message, MessageResponseTo.class);
    }
}
