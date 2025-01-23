package com.thaidot.search_service.controller;

import com.thaidot.search_service.dto.ApiResponse;
import com.thaidot.search_service.dto.response.ProfileSearchResponse;
import com.thaidot.search_service.entity.Profile;
import com.thaidot.search_service.service.ProfileSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileSearchController {

    private final ProfileSearchService profileSearchService;

    @GetMapping("/search")
    public ApiResponse<ProfileSearchResponse> searchProfiles(@RequestParam String query,
                                                             @RequestParam(defaultValue = "0", required = false) int page,
                                                             @RequestParam(defaultValue = "10", required = false) int size) {
        ProfileSearchResponse searchProfile = profileSearchService.searchProfiles(query, page, size);

        return ApiResponse.<ProfileSearchResponse>builder()
                .message("Search successful")
                .result(searchProfile)
                .build();
    }


}
