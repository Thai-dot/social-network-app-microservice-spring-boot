package com.thaidot.aggregator.controller;

import com.thaidot.aggregator.dto.ApiResponse;
import com.thaidot.aggregator.dto.PageResponse;
import com.thaidot.aggregator.dto.response.PostWithProfileResponse;
import com.thaidot.aggregator.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/my-posts")
    Mono<ApiResponse<PageResponse<PostWithProfileResponse>>> getPosts(@RequestParam(value = "page", defaultValue = "1"
            , required =
            false) int page, @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return postService.getMyPosts(page, size)
                .map(postWithProfileResponse -> ApiResponse.<PageResponse<PostWithProfileResponse>>builder().result(postWithProfileResponse).build());
    }
}
