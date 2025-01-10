package com.thaidot.aggregator.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostWithProfileResponse {
    String id;
    String content;
    String userId;
    UserProfileCreationResponse userProfile;
    Instant createdDate;
    Instant modifiedDate;
}
