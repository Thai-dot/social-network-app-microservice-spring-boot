package com.thaidot.post.controller;

import com.thaidot.post.dto.ApiResponse;
import com.thaidot.post.dto.PageResponse;
import com.thaidot.post.dto.request.PostRequest;
import com.thaidot.post.dto.response.PostResponse;
import com.thaidot.post.service.PostService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {
    PostService postService;
    Environment environment;

    @PostMapping("/")
    public ApiResponse<PostResponse> createPost(@RequestBody PostRequest request) {
        return ApiResponse.<PostResponse>builder().result(postService.createPost(request)).build();
    }

    @GetMapping("/my-posts")
    @Bulkhead(name = "post-service", fallbackMethod = "getMyPostsFallback")
    public ApiResponse<PageResponse<PostResponse>> getMyPosts(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        return ApiResponse.<PageResponse<PostResponse>>builder().result(postService.getMyPosts(page,size)).build();
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Post Service! on PORT: " + environment.getProperty("local.server.port");
    }

    public ApiResponse<PageResponse<PostResponse>> getMyPostsFallback(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            Throwable throwable
    ) {
        log.error("Fallback method called", throwable);
        return ApiResponse.<PageResponse<PostResponse>>builder().result(new PageResponse<>()).build();
    }
}