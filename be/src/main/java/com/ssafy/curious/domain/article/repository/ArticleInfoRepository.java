package com.ssafy.curious.domain.article.repository;

import com.ssafy.curious.domain.article.entity.ArticleInfoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArticleInfoRepository extends MongoRepository<ArticleInfoEntity, String> {
    Optional<List<ArticleInfoEntity>> findAllByOriginalUrl(String originalUrl);
    void deleteAllByOriginalUrl(String originUrl);
    Optional<ArticleInfoEntity> findByOriginalUrl(String originalUrl);
}
