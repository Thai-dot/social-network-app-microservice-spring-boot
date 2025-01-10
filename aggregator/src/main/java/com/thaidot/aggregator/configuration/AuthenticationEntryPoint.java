package com.thaidot.aggregator.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thaidot.aggregator.dto.ApiResponse;
import com.thaidot.aggregator.exception.ErrorCode;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class AuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    public AuthenticationEntryPoint() {
        this.objectMapper = new ObjectMapper(); // Use a shared ObjectMapper instance
    }

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(errorCode.getStatusCode());
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);


        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return Mono.fromSupplier(() -> {
                    try {
                        String jsonResponse = objectMapper.writeValueAsString(apiResponse);
                        DataBuffer buffer = response.bufferFactory().wrap(jsonResponse.getBytes());
                        return buffer;
                    } catch (Exception e) {
                        throw new RuntimeException("Error serializing ApiResponse", e);
                    }
                }).flatMap(buffer -> response.writeWith(Mono.just(buffer)));

    }
}
