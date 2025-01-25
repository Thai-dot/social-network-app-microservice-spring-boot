package com.thaidot.chat.service;

import com.thaidot.chat.dto.request.ChatMessageRequest;
import com.thaidot.chat.entity.ChatMessage;
import com.thaidot.chat.entity.ChatRoom;
import com.thaidot.chat.mapper.ChatMessageMapper;
import com.thaidot.chat.repository.ChatMessageRepository;
import com.thaidot.chat.utils.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;
    private final ChatRoomService chatRoomService;
    private final EncryptionUtil encryptionUtil;

    public ChatMessage saveMessage(String sender, ChatMessageRequest message) {
        String receiver = message.getReceiverIds().get(0);
        ChatRoom room = chatRoomService.findRoom(sender, receiver).orElse(null);
        if (room == null) {
            room = chatRoomService.createRoom(sender, receiver);
        }

        ChatMessage chatMessage = chatMessageMapper.toChatMessage(message);

        String encryptedMessage = encryptionUtil.encrypt(chatMessage.getContent());
        chatMessage.setContent(encryptedMessage);
        chatMessage.setSenderId(sender);
        chatMessage.setRoomId(room.getId());
        chatMessage.setTimestamp(Instant.now());

        return chatMessageRepository.save(chatMessage);
    }


}