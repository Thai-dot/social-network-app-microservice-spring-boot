package com.thaidot.search_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponse {
    private String userID;
    private String firstName;
    private String lastName;
    private String city;
    private LocalDate dob;
    private String avatarUrl;
}
