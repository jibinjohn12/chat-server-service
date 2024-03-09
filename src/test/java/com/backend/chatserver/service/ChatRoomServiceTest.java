package com.backend.chatserver.service;

import com.backend.chatserver.dao.entity.Message;
import com.backend.chatserver.dao.repository.MessageRepository;
import com.backend.chatserver.schema.ActionType;
import com.backend.chatserver.schema.ChatMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ChatRoomServiceTest {

    private ChatRoomService chatRoomService;

    @Mock
    private MessageRepository messageRepository;

    @Before
    public void setup() {
        this.chatRoomService = new ChatRoomService(messageRepository);
    }

    @Test
    public void saveMessage_returns_ChatMessage() {
        String roomName = "Room1";

        ChatMessage chat = new ChatMessage();
        chat.setContent("Test message");
        chat.setSender("User");
        chat.setType(ActionType.CHAT);

        Message message = Message.builder()
                .sender(chat.getSender())
                .content(chat.getContent())
                .room(roomName)
                .id(1L)
                .build();
        // Mock repository
        when(messageRepository.save(any())).thenReturn(message);

        ChatMessage returnedMsg = this.chatRoomService.saveMessage(chat, roomName);

        // Verify repository method was called
        verify(messageRepository, times(1)).save(any());
        assertEquals(returnedMsg.getId(), message.getId());
    }

    @Test
    public void saveMessage_handlesException() {
        String roomName = "Room1";

        ChatMessage chat = new ChatMessage();
        chat.setContent("Test message");
        chat.setSender("User");
        chat.setType(ActionType.CHAT);

        Message message = Message.builder()
                .sender(chat.getSender())
                .content(chat.getContent())
                .room(roomName)
                .id(1L)
                .build();
        // Mock repository
        when(messageRepository.save(any())).thenThrow(new RuntimeException("Connection Timed Out"));

        ChatMessage returnedMsg = this.chatRoomService.saveMessage(chat, roomName);

        // Verify repository method was called
        verify(messageRepository, times(1)).save(any());
        assertNotEquals(returnedMsg.getId(), message.getId());
    }

    @Test
    public void deleteMessage_returns_ChatMessage() {
        String roomName = "Room1";

        ChatMessage chat = new ChatMessage();
        chat.setContent("Test message");
        chat.setSender("User");
        chat.setType(ActionType.DELETE);

        Message message = Message.builder()
                .sender(chat.getSender())
                .content(chat.getContent())
                .room(roomName)
                .id(1L)
                .build();
        // Mock repository
        doNothing().when(messageRepository).deleteById(any());

        ChatMessage returnedMsg = this.chatRoomService.deleteMessage(chat, roomName);

        // Verify repository method was called
        verify(messageRepository, times(1)).deleteById(any());
        assertEquals(returnedMsg.getId(), message.getId());
    }

    @Test
    public void deleteMessage_handlesException() {
        String roomName = "Room1";

        ChatMessage chat = new ChatMessage();
        chat.setContent("Test message");
        chat.setSender("User");
        chat.setType(ActionType.DELETE);

        Message message = Message.builder()
                .sender(chat.getSender())
                .content(chat.getContent())
                .room(roomName)
                .id(1L)
                .build();
        // Mock repository
        doThrow(new RuntimeException()).when(messageRepository).deleteById(any());

        ChatMessage returnedMsg = this.chatRoomService.saveMessage(chat, roomName);

        assertNotEquals(returnedMsg.getId(), message.getId());
    }
}
