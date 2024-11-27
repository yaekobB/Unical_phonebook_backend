package com.api.user_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import com.api.user_management.io.entity.MessageEntity;
import com.api.user_management.io.repositories.MessageRepository;

import java.util.List;

@RestController
public class ChatController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/messages")
    public List<MessageEntity> getMessages() {
        return messageRepository.findAll();
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MessageEntity sendMessage(MessageEntity message) {
        return messageRepository.save(message);
    }
}
