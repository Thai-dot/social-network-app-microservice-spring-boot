package com.thaidot.profile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestResponseWithUserProfile extends  FriendRequestResponse{
   UserProfileCreationResponse userProfile;
}
