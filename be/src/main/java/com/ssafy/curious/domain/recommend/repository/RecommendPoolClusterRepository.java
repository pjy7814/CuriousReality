package com.ssafy.curious.domain.recommend.repository;

import com.ssafy.curious.domain.recommend.entity.RecommendPoolClusterEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RecommendPoolClusterRepository extends MongoRepository<RecommendPoolClusterEntity, String> {
    Optional<RecommendPoolClusterEntity> findByCreatedAtBetween(LocalDateTime startTime, LocalDateTime endTime);
}
