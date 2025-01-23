package com.thaidot.profile.service;

import com.thaidot.event.dto.UpdateAvatarProfileEvent;
import com.thaidot.profile.entity.UserProfile;
import com.thaidot.profile.exception.AppException;
import com.thaidot.profile.exception.ErrorCode;
import com.thaidot.profile.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsume {

    private final UserProfileRepository userProfileRepository;


    @KafkaListener(topics = "${kafka-topic.profile-avatar-update}")
    public void listenProfileAvatarUpdate(UpdateAvatarProfileEvent message) {
        UserProfile userProfile = userProfileRepository.findByUserID(message.getUserId()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        userProfile.setAvatarUrl(message.getAvatarUrl());
        userProfileRepository.save(userProfile);
    }

}
