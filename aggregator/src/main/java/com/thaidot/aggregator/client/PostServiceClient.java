package com.thaidot.aggregator.client;


import com.thaidot.aggregator.dto.ApiResponse;
import com.thaidot.aggregator.dto.PageResponse;
import com.thaidot.aggregator.dto.response.PostResponse;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

public interface PostServiceClient {
    @GetExchange(url = "/my-posts")
    Mono<ApiResponse<PageResponse<PostResponse>>> getMyPosts(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestHeader("Authorization") String authorizationHeader
    );

}