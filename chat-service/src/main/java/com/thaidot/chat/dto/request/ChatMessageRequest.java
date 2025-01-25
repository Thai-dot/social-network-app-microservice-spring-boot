package com.thaidot.chat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageRequest {
    private String senderId;
    private List<String> receiverIds;
    private String content;
    private Instant timestamp;
    private boolean isRead = false;
}
