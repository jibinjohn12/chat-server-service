package com.backend.chatserver.schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ChatMessage {

    private Long id;

    private String sender;

    private String content;

    private ActionType type;

}
