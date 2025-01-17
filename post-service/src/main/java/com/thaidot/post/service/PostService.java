package com.thaidot.post.service;

import com.thaidot.post.dto.PageResponse;
import com.thaidot.post.dto.request.PostRequest;
import com.thaidot.post.dto.response.PostResponse;
import com.thaidot.post.entity.Post;
import com.thaidot.post.mapper.PostMapper;
import com.thaidot.post.repository.PostRepository;
import com.thaidot.post.utils.stragety.dateformatter.DateFormatterContext;
import com.thaidot.post.utils.stragety.dateformatter.DateFormatterStrategy;
import com.thaidot.post.utils.stragety.dateformatter.DateFormatterStrategyConfig;
import com.thaidot.post.utils.stragety.dateformatter.impl.YearsFormatter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {
    PostRepository postRepository;
    PostMapper postMapper;
    DateFormatterContext context;

    public PostResponse createPost(PostRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Post post = Post.builder()
                .content(request.getContent())
                .userId(authentication.getName())
                .createdDate(Instant.now()).modifiedDate(Instant.now()).build();

        post = postRepository.save(post);

        return postMapper.toPostResponse(post);
    }

    public PageResponse<PostResponse> getMyPosts(int page, int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        Sort sort = Sort.by(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<Post> pageData = postRepository.findAllByUserId(userId, pageable);

        Map<Long, DateFormatterStrategy> strategyMap = DateFormatterStrategyConfig.getStrategyMap();

        List<PostResponse> postResponses = pageData.getContent().stream()
                .map(post -> {
                    Instant createdDate = post.getCreatedDate();
                    long seconds = Duration.between(createdDate, Instant.now()).getSeconds();

                    // Find the appropriate strategy based on the seconds elapsed
                    DateFormatterStrategy selectedStrategy = strategyMap.entrySet().stream()
                            .filter(entry -> seconds < entry.getKey())
                            .map(Map.Entry::getValue)
                            .findFirst()
                            .orElse(new YearsFormatter()); // Fallback to YearsFormatter if none match

                    context.setStrategy(selectedStrategy);

                    // Map Post to PostResponse and set the formatted date
                    PostResponse response = postMapper.toPostResponse(post);
                    response.setFormattedDate(context.format(createdDate));
                    return response;
                }).toList();

        return PageResponse.<PostResponse>builder()
                .currentPage(page)
                .totalPages(pageData.getTotalPages())
                .pageSize(pageData.getSize())
                .totalElements(pageData.getTotalElements())
                .data(postResponses)
                .build();
    }
}
