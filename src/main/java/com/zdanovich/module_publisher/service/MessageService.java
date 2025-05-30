package com.zdanovich.module_publisher.service;

import com.zdanovich.module_publisher.dto.request.MessageRequestTo;

import com.zdanovich.module_publisher.dto.response.MessageResponseTo;
import com.zdanovich.module_publisher.exception.EntityNotFoundException;
import com.zdanovich.module_publisher.model.Issue;
import com.zdanovich.module_publisher.model.Message;

import com.zdanovich.module_publisher.repository.IssueRepository;
import com.zdanovich.module_publisher.repository.MessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final ModelMapper modelMapper;
    private final MessageRepository messageRepository;
    private final IssueRepository issueRepository;
    private final RestClient restClient;

    @Autowired
    public MessageService(ModelMapper modelMapper, MessageRepository messageRepository, IssueRepository issueRepository, RestClient restClient) {
        this.modelMapper = modelMapper;
        this.messageRepository = messageRepository;
        this.issueRepository = issueRepository;
        this.restClient = restClient;
    }


    public List<MessageResponseTo> findAll() {
        return restClient.get().uri("").retrieve().body(List.class);
//        return messageRepository.findAll()
//                .stream()
//                .map(this::convertToResponse)
//                .collect(Collectors.toList());
    }

    public MessageResponseTo findById(long id) throws EntityNotFoundException {
        return restClient.get().uri("/" + id).retrieve().body(MessageResponseTo.class);
        //Message message = messageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("This message doesn't exist."));
        //return convertToResponse(message);
    }

    public List<MessageResponseTo> findByIssueId(long issueId) {
        return messageRepository.findByIssueId(issueId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public MessageResponseTo save(MessageRequestTo messageRequestTo) throws EntityNotFoundException{
//        Message message = convertToMessage(messageRequestTo);
//        messageRepository.save(message);
//        return convertToResponse(message);
        return restClient.post().uri("").body(messageRequestTo).retrieve().body(MessageResponseTo.class);
    }

    public MessageResponseTo update(MessageRequestTo messageRequestTo) throws EntityNotFoundException {
//        messageRepository.findById(messageRequestTo.getId()).orElseThrow(() -> new EntityNotFoundException("This message doesn't exist."));
//
//        Message updatedMessage = convertToMessage(messageRequestTo);
//        updatedMessage.setId(messageRequestTo.getId());
//        messageRepository.save(updatedMessage);
//
//        return convertToResponse(updatedMessage);
        return restClient.put().uri("").body(messageRequestTo).retrieve().body(MessageResponseTo.class);
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
//        messageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Message doesn't exist."));
//        messageRepository.deleteById(id);
        restClient.delete().uri("/" + id);
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
