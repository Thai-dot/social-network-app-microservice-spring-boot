package com.devteria.notification.dto.request;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {
    private Sender sender;
    private List<Recipient> to;
    private String subject;
    private String htmlContent;
}
