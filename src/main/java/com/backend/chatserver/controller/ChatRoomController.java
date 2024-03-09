package com.backend.chatserver.controller;

import com.backend.chatserver.schema.ActionType;
import com.backend.chatserver.schema.ChatMessage;
import com.backend.chatserver.service.ChatRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
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
    public boolean sendMessage(@Payload ChatMessage chat) {
        log.info("Message Request");
        if (ActionType.CHAT.equals(chat.getType())) {
            chat = chatRomService.saveMessage(chat, this.topic);
        } else if (ActionType.DELETE.equals(chat.getType())) {
            chat = chatRomService.deleteMessage(chat, this.topic);
        }

        this.messagingTemplate.convertAndSend(this.topic, chat);
        return true;
    }

    @MessageMapping("/register")
    public void userJoin(@Payload ChatMessage chat, SimpMessageHeaderAccessor headerAccessor) {
        log.info("Register Request");
        String user = chat.getSender();
        String sessionId = headerAccessor.getSessionId();
        log.info("Subscriber Start! User:{}, Session:{}", user, sessionId);
        headerAccessor.getSessionAttributes().put("user", user);
        this.messagingTemplate.convertAndSend(this.topic, chat);
    }
}
