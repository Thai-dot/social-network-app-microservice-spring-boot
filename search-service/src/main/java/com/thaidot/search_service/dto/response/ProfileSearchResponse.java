package com.thaidot.search_service.dto.response;

import com.thaidot.search_service.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.elasticsearch.core.TotalHitsRelation;

import java.util.List;

@Data
@AllArgsConstructor
public class ProfileSearchResponse {
    private List<Profile> profiles;
    private long totalHits;
    private TotalHitsRelation totalHitsRelation;
    private double maxScore;
}