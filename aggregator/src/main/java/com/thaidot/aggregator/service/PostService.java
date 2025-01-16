package com.thaidot.aggregator.service;

import com.thaidot.aggregator.client.PostServiceClient;
import com.thaidot.aggregator.client.ProfileServiceClient;
import com.thaidot.aggregator.dto.PageResponse;
import com.thaidot.aggregator.dto.response.PostWithProfileResponse;
import com.thaidot.aggregator.exception.AppException;
import com.thaidot.aggregator.exception.ErrorCode;
import com.thaidot.aggregator.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostServiceClient postServiceClient;
    private final ProfileServiceClient profileServiceClient;
    private final PostMapper postMapper;

    public Mono<PageResponse<PostWithProfileResponse>> getMyPosts(int page, int size) {
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> (JwtAuthenticationToken) securityContext.getAuthentication())
                .map(JwtAuthenticationToken::getToken)
                .map(jwt -> jwt.getTokenValue())
                .flatMap(token ->
                        postServiceClient.getMyPosts(page, size, "Bearer " + token)
                                .flatMap(response -> {
                                    if (response.getCode() == 1000) {
                                        if (response.getResult().getData().isEmpty()) {
                                            return Mono.error(new AppException(ErrorCode.NO_POST_FOUND));
                                        }
                                        return Mono.just(response.getResult());
                                    } else {
                                        return Mono.error(new AppException(ErrorCode.FETCHING_ERROR));
                                    }
                                })
                                .flatMap(postResponses ->
                                        profileServiceClient.getProfileByUserId(postResponses.getData().get(0).getUserId(),
                                                        "Bearer " + token)
                                                .map(profileResponse -> {
                                                    List<PostWithProfileResponse> postWithProfileResponse =
                                                            postResponses.getData().stream()
                                                                    .map(postResponse -> {
                                                                        PostWithProfileResponse response = postMapper.toPostWithProfileResponse(postResponse);
                                                                        response.setUserProfile(profileResponse.getResult());
                                                                        return response;
                                                                    })
                                                                    .toList();
                                                    PageResponse<PostWithProfileResponse> pageResponse = PageResponse.<PostWithProfileResponse>builder()
                                                            .currentPage(postResponses.getCurrentPage())
                                                            .totalPages(postResponses.getTotalPages())
                                                            .pageSize(postResponses.getPageSize())
                                                            .totalElements(postResponses.getTotalElements())
                                                            .data(postWithProfileResponse)
                                                            .build();
                                                    return pageResponse;
                                                })
                                )
                );
    }
}
