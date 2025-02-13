package com.thaidot.identity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileCreationResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private LocalDate dob;
}
