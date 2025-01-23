package com.thaidot.search_service.service;

import com.thaidot.search_service.dto.response.ProfileSearchResponse;
import com.thaidot.search_service.entity.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.data.elasticsearch.core.SearchHit;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileSearchService {
    private final ElasticsearchOperations elasticsearchOperations;

    public ProfileSearchResponse searchProfiles(String query, int page, int size) {
        elasticsearchOperations.indexOps(Profile.class).refresh();
        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(q -> q.multiMatch(m -> m.fields("firstName", "lastName", "city").query(query)
                ))
                .withPageable(PageRequest.of(page, size))
                .build();
        SearchHits<Profile> searchHits = elasticsearchOperations.search(searchQuery, Profile.class);

        List<Profile> profiles = searchHits.stream()
                .map(SearchHit::getContent).toList();

        return new ProfileSearchResponse(profiles, searchHits.getTotalHits(), searchHits.getTotalHitsRelation(), searchHits.getMaxScore());
    }
}
