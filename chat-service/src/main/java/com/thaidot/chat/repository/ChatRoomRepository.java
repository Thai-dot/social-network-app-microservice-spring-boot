package com.thaidot.chat.repository;

import com.thaidot.chat.entity.ChatMessage;
import com.thaidot.chat.entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    @Query("{ 'participantIds': { $all: ?0 } }")
    Optional<ChatRoom> findByParticipantIdsContainingBoth(List<String> participantIds);
}