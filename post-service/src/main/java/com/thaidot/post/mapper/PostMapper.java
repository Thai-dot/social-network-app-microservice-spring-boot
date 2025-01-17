package com.thaidot.post.mapper;

import com.thaidot.post.dto.response.PostResponse;
import com.thaidot.post.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostResponse toPostResponse(Post post);
}
