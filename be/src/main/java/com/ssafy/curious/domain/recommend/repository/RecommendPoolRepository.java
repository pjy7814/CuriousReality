package com.ssafy.curious.domain.recommend.repository;

import com.ssafy.curious.domain.recommend.entity.RecommendPoolEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecommendPoolRepository extends MongoRepository<RecommendPoolEntity, String> {
    RecommendPoolEntity findByMemberId(Long memberId);
}
