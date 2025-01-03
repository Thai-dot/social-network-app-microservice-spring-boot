package com.devteria.identity.client;


import com.devteria.identity.dto.request.ApiResponse;
import com.devteria.identity.dto.request.UserProfileCreationRequest;
import com.devteria.identity.dto.response.UserProfileCreationResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface ProfileClient {

    @PostExchange(value = "/internal/user-profile", contentType = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ApiResponse<UserProfileCreationResponse>>  createUserProfile(@RequestBody UserProfileCreationRequest request);

}