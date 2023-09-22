package com.ssafy.curious.domain.article.repository;

import com.ssafy.curious.domain.article.entity.ArticleInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleInfoRepository extends MongoRepository<ArticleInfo, String> {
}
