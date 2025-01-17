package com.thaidot.profile.dto.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailRequest {
    private Recipient to;
    private String subject;
    private String htmlContent;
}
