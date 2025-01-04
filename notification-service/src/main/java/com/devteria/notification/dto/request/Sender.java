package com.devteria.notification.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sender {
    private String name;
    private String email;
}
