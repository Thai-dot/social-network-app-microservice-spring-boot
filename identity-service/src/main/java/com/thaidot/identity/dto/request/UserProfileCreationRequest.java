package com.thaidot.identity.dto.request;

import com.thaidot.identity.validator.DobConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileCreationRequest {

    String userID;
    String firstName;
    String lastName;
    String email;
    @DobConstraint(min = 10, message = "INVALID_DOB")
    LocalDate dob;

    String city;
}