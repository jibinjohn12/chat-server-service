package com.backend.chatserver.controller;

import com.backend.chatserver.schema.ChatMessage;
import com.backend.chatserver.service.ChatRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final ChatRoomService chatRomService;

    @Autowired
    public ChatRoomController(@Value("${app.websocket.topic}") String topic,
                              SimpMessageSendingOperations messagingTemplate,
                              ChatRoomService chatRomService) {
        this.topic = topic;
        this.messagingTemplate = messagingTemplate;
        this.chatRomService = chatRomService;
    }

    @MessageMapping("/send")
    public void sendMessage(@Payload ChatMessage chat) {
        log.info("Message Request");
        chat = chatRomService.saveMessage(chat, this.topic);
        this.messagingTemplate.convertAndSend(this.topic, chat);
    }

    @MessageMapping("/register")
    public void userJoin(@Payload ChatMessage chat, SimpMessageHeaderAccessor headerAccessor) {
        log.info("Register Request");
        headerAccessor.getSessionAttributes().put("user", chat.getSender());
        this.messagingTemplate.convertAndSend(this.topic, chat);
    }
}
