package com.backend.chatserver.config;

import com.backend.chatserver.schema.ActionType;
import com.backend.chatserver.schema.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messagingTemplate;
    private final String topic;

    @Autowired
    public WebSocketEventListener(@Value("${app.websocket.topic}") String topic,
                                  SimpMessageSendingOperations messagingTemplate) {
        this.topic = topic;
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleWebSocketDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String user = String.valueOf(headerAccessor.getSessionAttributes().get("user"));
        if (user == null || user.length() == 0)
            user = "Unknown User";
        ChatMessage chat = ChatMessage.builder()
                .type(ActionType.LEAVE)
                .sender(user)
                .build();
        messagingTemplate.convertAndSend(this.topic, chat);
    }
}
