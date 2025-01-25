package com.thaidot.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "chat_room")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatRoom {
    @Id
    private String id;
    private List<String> participantIds;
}