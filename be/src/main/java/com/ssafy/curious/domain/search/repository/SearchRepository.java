package com.ssafy.curious.domain.search.repository;

import com.ssafy.curious.domain.article.entity.ArticleInfoEntity;
import com.ssafy.curious.domain.search.entity.SearchEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public interface SearchRepository extends MongoRepository<SearchEntity,String> {

    List<SearchEntity> findByCategory1AndCategory2AndCreatedAtBetween(String category1, String category2, Date startDate, Date endDate);
    List<SearchEntity>findByCategory1AndCategory2(String category1,String category2);
}
