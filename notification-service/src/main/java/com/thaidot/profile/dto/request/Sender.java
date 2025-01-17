package com.thaidot.profile.dto.request;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sender {
    private String name;
    private String email;
}
