package com.thaidot.profile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestResponse {
    private String senderID;
    private String receiverID;
    private boolean accepted=false;
    private Instant requestDate = Instant.now();

}
