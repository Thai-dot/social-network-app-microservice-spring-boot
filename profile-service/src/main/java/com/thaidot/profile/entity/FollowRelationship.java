package com.thaidot.profile.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RelationshipProperties
public class FollowRelationship {

    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private UserProfile followee;

    private LocalDateTime followedAt;

}