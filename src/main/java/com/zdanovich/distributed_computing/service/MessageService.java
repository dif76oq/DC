package com.zdanovich.distributed_computing.service;

import com.zdanovich.distributed_computing.dao.InMemoryMessageDao;
import com.zdanovich.distributed_computing.dto.request.MessageRequestTo;

import com.zdanovich.distributed_computing.dto.response.MessageResponseTo;
import com.zdanovich.distributed_computing.exception.EntityNotFoundException;
import com.zdanovich.distributed_computing.model.Message;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final ModelMapper modelMapper;
    private final InMemoryMessageDao messageDao;

    @Autowired
    public MessageService(ModelMapper modelMapper, InMemoryMessageDao messageDao) {
        this.modelMapper = modelMapper;
        this.messageDao = messageDao;
    }


    public List<MessageResponseTo> findAll() {
        return messageDao.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public MessageResponseTo findById(long id) throws EntityNotFoundException {
        Message message = messageDao.findById(id).orElseThrow(() -> new EntityNotFoundException("This message doesn't exist."));

        return convertToResponse(message);
    }

    public List<MessageResponseTo> findByIssueId(long issueId) {
        return messageDao.findByIssueId(issueId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public MessageResponseTo save(MessageRequestTo messageRequestTo) {
        Message message = convertToMessage(messageRequestTo);
        messageDao.save(message);
        return convertToResponse(message);
    }

    public MessageResponseTo update(MessageRequestTo messageRequestTo) throws EntityNotFoundException {
        messageDao.findById(messageRequestTo.getId()).orElseThrow(() -> new EntityNotFoundException("This message doesn't exist."));

        Message updatedMessage = convertToMessage(messageRequestTo);
        updatedMessage.setId(messageRequestTo.getId());
        messageDao.save(updatedMessage);

        return convertToResponse(updatedMessage);
    }

    public MessageResponseTo partialUpdate(MessageRequestTo messageRequestTo) throws EntityNotFoundException {

        Message message = messageDao.findById(messageRequestTo.getId()).orElseThrow(() -> new EntityNotFoundException("Message not found"));

        if (messageRequestTo.getContent() != null) {
            message.setContent(messageRequestTo.getContent());
        }
        if (messageRequestTo.getIssueId() != 0) {
            message.setIssueId(messageRequestTo.getIssueId());
        }

        messageDao.save(message);

        return convertToResponse(message);
    }

    public void delete(long id) throws EntityNotFoundException {
        messageDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Message doesn't exist."));
        messageDao.deleteById(id);
    }

    private Message convertToMessage(MessageRequestTo messageRequestTo) {
        return this.modelMapper.map(messageRequestTo, Message.class);
    }

    private MessageResponseTo convertToResponse(Message message) {
        return this.modelMapper.map(message, MessageResponseTo.class);
    }
}
