package com.ssafy.curious.domain.recommend.repository;

import com.ssafy.curious.domain.recommend.entity.RecommendPoolClusterEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;

public interface RecommendPooClusterRepository extends MongoRepository<RecommendPoolClusterEntity, String> {
    RecommendPoolClusterEntity findByCreatedAtBetween(LocalDateTime startTime, LocalDateTime endTime);
}
