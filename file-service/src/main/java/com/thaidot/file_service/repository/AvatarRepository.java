package com.thaidot.file_service.repository;


import com.thaidot.file_service.entity.Avatar;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvatarRepository extends MongoRepository<Avatar, String> {
    Optional<Avatar> findByUserID(String userID);
    Optional<Avatar> findByUrl(String url);
    Optional<Avatar> findById(String id);

    void deleteByUserID(String userID);
}
