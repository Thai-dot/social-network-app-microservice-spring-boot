package com.thaidot.chat.controller;

import com.thaidot.chat.dto.request.ChatMessageRequest;
import com.thaidot.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/private-message")
    @SendToUser("/queue/reply")
    public void sendPrivateMessage(@Payload ChatMessageRequest chatMessage, Authentication authentication) {
        chatService.sendMessage(chatMessage, authentication);
    }
}