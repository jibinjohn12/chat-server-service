package com.backend.chatserver.controller;

import com.backend.chatserver.schema.ActionType;
import com.backend.chatserver.schema.ChatMessage;
import com.backend.chatserver.service.ChatRoomService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ChatRoomControllerTests {

    private final String topic = "/topic/test";
    private ChatRoomController chatRoomController;
    @Mock
    private SimpMessageSendingOperations messagingTemplate;
    @Mock
    private ChatRoomService chatRomService;

    @Before
    public void setup() {
        this.chatRoomController = new ChatRoomController(topic, messagingTemplate, chatRomService);
    }

    @Test
    public void testSendMessage_send_returns_success() {
        // Mock data
        ChatMessage chat = new ChatMessage();
        chat.setContent("Test message");
        chat.setSender("User");
        chat.setType(ActionType.CHAT);


        // Mock service behavior
        when(chatRomService.saveMessage(any(), any())).thenReturn(chat);

        // Call controller method
        boolean success = chatRoomController.sendMessage(chat);

        // Verify service method was called
        verify(chatRomService, times(1)).saveMessage(any(), any());

        // Verify result
        assertEquals(success, true);
    }

    @Test
    public void testSendMessage_delete_returns_success() {
        // Mock data
        ChatMessage chat = new ChatMessage();
        chat.setContent("Test message");
        chat.setSender("User");
        chat.setType(ActionType.DELETE);

        // Mock service behavior
        when(chatRomService.deleteMessage(any(), any())).thenReturn(chat);

        // Call controller method
        boolean success = chatRoomController.sendMessage(chat);

        // Verify service method was called
        verify(chatRomService, times(1)).deleteMessage(any(), any());

        // Verify result
        assertEquals(success, true);
    }
}
