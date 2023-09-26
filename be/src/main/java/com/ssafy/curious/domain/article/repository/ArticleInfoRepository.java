package com.ssafy.curious.domain.article.repository;

import com.ssafy.curious.domain.article.entity.ArticleInfoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleInfoRepository extends MongoRepository<ArticleInfoEntity, String> {
}
