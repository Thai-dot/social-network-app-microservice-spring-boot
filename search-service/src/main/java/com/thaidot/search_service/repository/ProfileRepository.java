package com.thaidot.search_service.repository;

import com.thaidot.search_service.entity.Profile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


@EnableElasticsearchRepositories
public interface ProfileRepository extends ElasticsearchRepository<Profile, String> {
}
