package com.thaidot.aggregator.client;

import com.thaidot.aggregator.dto.ApiResponse;
import com.thaidot.aggregator.dto.response.UserProfileCreationResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

public interface ProfileServiceClient {
    @GetExchange("/user-profile/id/{userId}")
    Mono<ApiResponse<UserProfileCreationResponse>> getProfileByUserId(@PathVariable String userId, @RequestHeader("Authorization") String authorizationHeader);
}