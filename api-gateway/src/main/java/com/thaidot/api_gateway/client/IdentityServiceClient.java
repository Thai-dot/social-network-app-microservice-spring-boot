package com.thaidot.api_gateway.client;

import com.thaidot.api_gateway.dto.request.IntrospectRequest;
import com.thaidot.api_gateway.dto.response.ApiResponse;
import com.thaidot.api_gateway.dto.response.IntrospectResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface IdentityServiceClient {

    @PostExchange(url="/auth/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request);
}
