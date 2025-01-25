package com.thaidot.chat.service;

import com.thaidot.chat.dto.request.ChatMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    public void sendMessage(ChatMessageRequest chatMessage, Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException("Authentication is missing!");
        }
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String sender = jwt.getSubject().toString(); // Adjust claim based on your provider

        chatService.saveMessage(sender,chatMessage);
        messagingTemplate.convertAndSendToUser(chatMessage.getReceiverIds().get(0), "/queue/reply", chatMessage);
    }
}
