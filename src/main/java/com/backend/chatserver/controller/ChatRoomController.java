package com.backend.chatserver.controller;

import com.backend.chatserver.schema.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class ChatRoomController {
    private final String topic;
    private final SimpMessageSendingOperations messagingTemplate;

    public ChatRoomController(@Value("${app.websocket.topic}") String topic,
                              SimpMessageSendingOperations messagingTemplate) {
        this.topic = topic;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/send")
    public void sendMessage(@Payload ChatMessage chat) {
        log.info("Message Request");
        this.messagingTemplate.convertAndSend(this.topic, chat);
    }

    @MessageMapping("/register")
    public void userJoin(@Payload ChatMessage chat, SimpMessageHeaderAccessor headerAccessor) {
        log.info("Register Request");
        headerAccessor.getSessionAttributes().put("user", chat.getSender());
        this.messagingTemplate.convertAndSend(this.topic, chat);
    }
}
