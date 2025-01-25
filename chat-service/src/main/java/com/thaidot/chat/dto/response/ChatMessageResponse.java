package com.thaidot.chat.dto.response;

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
public class ChatMessageResponse {
    private String senderId;
    private List<String> receiverIds;
    private String content;
    private Instant timestamp;
    private String roomId;
    private boolean isRead;
}
