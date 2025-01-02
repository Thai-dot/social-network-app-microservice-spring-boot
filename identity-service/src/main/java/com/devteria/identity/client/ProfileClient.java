package com.devteria.identity.client;


import com.devteria.identity.dto.request.UserProfileCreationRequest;
import com.devteria.identity.dto.response.ApiResponse;
import com.devteria.identity.dto.response.UserProfileCreationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface ProfileClient {

    @PostExchange("/internal/user-profile/")
    ResponseEntity<ApiResponse<UserProfileCreationResponse>>  createUserProfile(@RequestBody UserProfileCreationRequest request);

}