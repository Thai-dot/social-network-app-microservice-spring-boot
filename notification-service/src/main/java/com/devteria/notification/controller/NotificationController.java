package com.devteria.notification.controller;

import com.devteria.event.dto.NotificationEvent;
import com.devteria.notification.dto.request.Recipient;
import com.devteria.notification.dto.request.SendEmailRequest;
import com.devteria.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationController {
    private final EmailService emailController;

    @KafkaListener(topics = "notification-delivery")
    public void listenNotificationDelivery(NotificationEvent message) {

        emailController.sendEmail(SendEmailRequest.builder().to(Recipient.builder().email(message.getRecipient()).build()).subject(message.getSubject()).htmlContent(message.getBody()).build());
    }
}
