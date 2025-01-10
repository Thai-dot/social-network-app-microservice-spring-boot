package com.thaidot.aggregator.mapper;


import com.thaidot.aggregator.dto.response.PostResponse;
import com.thaidot.aggregator.dto.response.PostWithProfileResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostWithProfileResponse toPostWithProfileResponse(PostResponse postResponse);
}
