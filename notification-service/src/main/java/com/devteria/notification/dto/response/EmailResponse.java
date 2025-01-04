package com.devteria.notification.dto.response;

import com.devteria.notification.dto.request.Recipient;
import com.devteria.notification.dto.request.Sender;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailResponse {
    private String messageId;
}
