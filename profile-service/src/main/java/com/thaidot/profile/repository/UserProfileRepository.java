package com.thaidot.profile.repository;

import com.thaidot.profile.entity.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserProfileRepository extends Neo4jRepository<UserProfile, String> {
    Optional<UserProfile> findByUserID(String userId);

    // Query to fetch paginated friends
    @Query(value = """
                MATCH (u:user_profile {userID: $userID})-[:FRIEND]-(friend:user_profile)
                RETURN DISTINCT friend
            """,
            countQuery = """
                        MATCH (u:user_profile {userID: $userID})-[:FRIEND]-(friend:user_profile)
                        RETURN count(DISTINCT friend)
                    """)
    Page<UserProfile> findFriendsOfUser(String userID, Pageable pageable);

    // follow user method
    @Query("""
                MATCH (u1:user_profile {userID: $followerID}), (u2:user_profile {userID: $followeeID})
                MERGE (u1)-[:FOLLOWS]->(u2)
            """)
    void followUser(String followerID, String followeeID);

    // Make add friend function here
    @Query("""
                MATCH (u1:user_profile {userID: $userId1}), (u2:user_profile {userID: $userId2})
                MERGE (u1)-[:FRIEND]-(u2)
            """)
    void addFriend(String userId1, String userId2);

    @Query("""
                MATCH (u1:user_profile {userID: $userId1})-[:FRIEND]-(u2:user_profile {userID: $userId2})
                RETURN count(u2) > 0
            """)
    boolean areUsersFriends(String userId1, String userId2);

    @Query("""
                MATCH (u1:user_profile {userID: $userId1})-[r:FRIEND]-(u2:user_profile {userID: $userId2})
                DELETE r
            """)
    void unfriendUsers(String userId1, String userId2);

    // check if is user1 already followed user2
    @Query("""
                MATCH (u1:user_profile {userID: $userId1})-[r:FOLLOWS]-(u2:user_profile {userID: $userId2})
                RETURN count(r) > 0
            """)
    boolean isUserFollowingOtherUser(String userId1, String userId2);

    // get all following users of a user
    @Query(value = """
            MATCH (u:user_profile {userID: $userId})-[:FOLLOWS]->(followee:user_profile)
            RETURN followee
        """,
            countQuery = """
            MATCH (u:user_profile {userID: $userId})-[:FOLLOWS]->(followee:user_profile)
            RETURN count(followee)
        """)
    Page<UserProfile> getFollowing(String userId, Pageable pageable);


    // get all followers of a user
    @Query(value = """
            MATCH (u:user_profile {userID: $userId})<-[:FOLLOWS]-(follower:user_profile)
            RETURN follower
        """,
            countQuery = """
            MATCH (u:user_profile {userID: $userId})<-[:FOLLOWS]-(follower:user_profile)
            RETURN count(follower)
        """)
    Page<UserProfile> getFollowers(String userId, Pageable pageable);

    // unfollow a user
    @Query("""
            MATCH (u1:user_profile {userID: $follower})-[r:FOLLOWS]->(u2:user_profile {userID: $followee})
            DELETE r
        """)
    void unfollowUser(String follower, String followee);

    // get all friends of a user
    @Query(value = """
            MATCH (u:user_profile {userID: $userId})-[:FRIEND]-(friend:user_profile)
            RETURN DISTINCT friend
        """,
            countQuery = """
            MATCH (u:user_profile {userID: $userId})-[:FRIEND]-(friend:user_profile)
            RETURN count(DISTINCT friend)
        """)
    Page<UserProfile> getFriends(String userId, Pageable pageable);
}
