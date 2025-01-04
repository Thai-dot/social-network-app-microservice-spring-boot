package com.devteria.notification.service;

import com.devteria.notification.client.EmailClient;
import com.devteria.notification.dto.request.EmailRequest;
import com.devteria.notification.dto.request.SendEmailRequest;
import com.devteria.notification.dto.request.Sender;
import com.devteria.notification.dto.response.EmailResponse;
import com.devteria.notification.exception.AppException;
import com.devteria.notification.exception.ErrorCode;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailClient emailClient;
    @Value("${email.api-key}")
    private String apiKey;

    public EmailResponse sendEmail(SendEmailRequest sendEmailRequest) {
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder().name("Nguyen Thai").email("nguyenhoangthai7871@gmail.com").build())
                .to(List.of(sendEmailRequest.getTo()))
                .subject(sendEmailRequest.getSubject())
                .htmlContent(sendEmailRequest.getHtmlContent())
                .build();
        try {
            return emailClient.sendEmail(apiKey, emailRequest);

        } catch (FeignException e) {
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }
}
