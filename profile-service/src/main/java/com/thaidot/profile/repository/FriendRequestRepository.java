package com.thaidot.profile.repository;

import com.thaidot.profile.entity.FriendRequest;
import com.thaidot.profile.entity.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface FriendRequestRepository extends Neo4jRepository<FriendRequest, String> {
    Optional<FriendRequest> findBySenderIDAndReceiverID(String senderID, String receiverID);
    boolean existsBySenderIDAndReceiverID(String senderID, String receiverID);
    void deleteBySenderIDAndReceiverID(String senderID, String receiverID);

    // Get list of friend requests

    Page<FriendRequest> findByReceiverID(String receiverID, Pageable pageable);
}
