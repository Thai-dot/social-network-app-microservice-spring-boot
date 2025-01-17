package com.thaidot.file_service.service;

import com.thaidot.event.dto.UpdateAvatarProfileEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka-topic.profile-avatar-update}")
    private String profileAvatarUpdateTopic;

    public void sendProfileAvatarUpdateMessage(UpdateAvatarProfileEvent event) {
        kafkaTemplate.send(profileAvatarUpdateTopic, event);
    }
}
