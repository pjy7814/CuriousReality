package com.ssafy.curious.domain.article.repository;

import com.ssafy.curious.domain.article.entity.LikedArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedArticleRepository extends JpaRepository<LikedArticleEntity, Long> {
}
