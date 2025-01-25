package com.thaidot.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "chat_message")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatMessage {
    @Id
    private String id;
    private String senderId;
    private List<String> receiverIds;
    private String content;
    private Instant timestamp;
    private String roomId;
    private boolean isRead = false;
}