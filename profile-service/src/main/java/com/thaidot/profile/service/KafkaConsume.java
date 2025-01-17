package com.thaidot.profile.service;

import com.thaidot.event.dto.UpdateAvatarProfileEvent;
import com.thaidot.profile.entity.UserProfile;
import com.thaidot.profile.exception.AppException;
import com.thaidot.profile.exception.ErrorCode;
import com.thaidot.profile.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsume {

    private final UserProfileRepository userProfileRepository;


    @KafkaListener(topics = "profile-avatar-update")
    public void listenProfileAvatarUpdate(UpdateAvatarProfileEvent message) {
        log.info("Received message: {}", message);
        UserProfile userProfile = userProfileRepository.findByUserID(message.getUserId()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        userProfile.setAvatarUrl(message.getAvatarUrl());
        userProfileRepository.save(userProfile);
    }

}
