package com.backend.chatserver.service;

import com.backend.chatserver.dao.entity.Message;
import com.backend.chatserver.dao.repository.MessageRepository;
import com.backend.chatserver.schema.ActionType;
import com.backend.chatserver.schema.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChatRoomService {

    private final MessageRepository messageRepository;

    @Autowired
    public ChatRoomService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public ChatMessage saveMessage(ChatMessage msg, String room) {
        // TODO: Try to batch #no of writes per session.
        // Redesign to save messages as a batch by generating temporary id
        try {
            Message message = Message.builder()
                    .sender(msg.getSender())
                    .content(msg.getContent())
                    .room(room)
                    .build();
            message = this.messageRepository.save(message);
            msg.setId(message.getId());
        } catch (Exception e) {
            log.error("Failed to save message", e);
        }
        return msg;
    }

    public ChatMessage deleteMessage(ChatMessage msg, String room) {
        // TODO: Try to batch #no of writes per session.
        // Redesign to save messages as a batch by generating temporary id
        try {
            msg.setType(ActionType.DELETE);
            this.messageRepository.deleteById(msg.getId());
            return msg;
        } catch (Exception e) {
            log.error("Failed to delete message", e);
        }
        return msg;
    }
}
