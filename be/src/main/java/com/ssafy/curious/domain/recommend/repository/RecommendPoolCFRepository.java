package com.ssafy.curious.domain.recommend.repository;

import com.ssafy.curious.domain.recommend.entity.RecommendPoolCFEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;

public interface RecommendPoolCFRepository extends MongoRepository<RecommendPoolCFEntity, String> {
    RecommendPoolCFEntity findByCreatedAtBetweenAndMemberId(LocalDateTime startTime, LocalDateTime endTime, Long memberId);
}
