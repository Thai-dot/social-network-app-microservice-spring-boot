package com.thaidot.chat.configuration;

import com.thaidot.chat.dto.ChatMessage;
import com.thaidot.chat.dto.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if (username != null) {
           var chatMessage = ChatMessage.builder()
                   .content("User has left the chat")
                   .sender(username)
                   .type(MessageType.LEAVE)
                   .build();

              log.info("User Disconnected : {}", chatMessage);


        }
    }
}
