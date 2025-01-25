package com.thaidot.chat.service;

import com.thaidot.chat.entity.ChatRoom;
import com.thaidot.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public ChatRoom createRoom(String firstUserId, String secondUserId) {
        List<String> participants = List.of(firstUserId, secondUserId);
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setParticipantIds(participants);
        return chatRoomRepository.save(chatRoom);
    }

    public Optional<ChatRoom> findRoom(String firstUserId, String secondUserId) {
        List<String> participants = List.of(firstUserId, secondUserId);
        return chatRoomRepository.findByParticipantIdsContainingBoth(participants);
    }

}
