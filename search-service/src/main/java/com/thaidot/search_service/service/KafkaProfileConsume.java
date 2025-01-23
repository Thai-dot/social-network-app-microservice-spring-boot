package com.thaidot.search_service.service;

import com.thaidot.event.dto.ProfileEvent;
import com.thaidot.search_service.mapper.ProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KafkaProfileConsume {

    private final ProfileService userProfileRepository;
    private final ProfileMapper profileMapper;

    @KafkaListener(topics = "${kafka-topic.add-profile-elastic}")
    public void listenProfileAvatarUpdate(ProfileEvent message) {
        userProfileRepository.saveProfile(profileMapper.toProfileRequestFromEvent(message));
    }
}
