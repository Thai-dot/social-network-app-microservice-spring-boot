package com.thaidot.profile.service;

import com.thaidot.profile.client.EmailClient;
import com.thaidot.profile.dto.request.EmailRequest;
import com.thaidot.profile.dto.request.SendEmailRequest;
import com.thaidot.profile.dto.request.Sender;
import com.thaidot.profile.dto.response.EmailResponse;
import com.thaidot.profile.exception.AppException;
import com.thaidot.profile.exception.ErrorCode;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
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
