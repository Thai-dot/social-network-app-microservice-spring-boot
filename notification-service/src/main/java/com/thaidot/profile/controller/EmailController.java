package com.thaidot.profile.controller;

import com.thaidot.profile.dto.ApiResponse;
import com.thaidot.profile.dto.request.SendEmailRequest;
import com.thaidot.profile.dto.response.EmailResponse;
import com.thaidot.profile.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
