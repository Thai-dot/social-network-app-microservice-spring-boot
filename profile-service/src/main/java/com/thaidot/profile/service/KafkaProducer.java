package com.thaidot.profile.service;

import com.thaidot.event.dto.ProfileEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka-topic.add-profile-elastic}")
    private String addProfileElasticTopic;

    public void sendAddProfileElastic(ProfileEvent event) {
        kafkaTemplate.send(addProfileElasticTopic, event);
    }
}
