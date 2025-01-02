package com.thaidot.api_gateway.service;

import com.thaidot.api_gateway.client.IdentityServiceClient;
import com.thaidot.api_gateway.dto.request.IntrospectRequest;
import com.thaidot.api_gateway.dto.response.ApiResponse;
import com.thaidot.api_gateway.dto.response.IntrospectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class IdentityService {
    private final IdentityServiceClient identityServiceClient;

    public Mono<ApiResponse<IntrospectResponse>> introspect(String token){
        return identityServiceClient.introspect(IntrospectRequest.builder().token(token).build());
    }
}
