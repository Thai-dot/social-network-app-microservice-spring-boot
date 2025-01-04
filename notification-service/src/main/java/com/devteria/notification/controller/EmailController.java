package com.devteria.notification.controller;

import com.devteria.notification.dto.ApiResponse;
import com.devteria.notification.dto.request.SendEmailRequest;
import com.devteria.notification.dto.response.EmailResponse;
import com.devteria.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/email/send")
    public ApiResponse<EmailResponse> sendEmail(@RequestBody SendEmailRequest sendEmailRequest) {
        return ApiResponse.<EmailResponse>builder().result(emailService.sendEmail(sendEmailRequest)).build();
    }


}
