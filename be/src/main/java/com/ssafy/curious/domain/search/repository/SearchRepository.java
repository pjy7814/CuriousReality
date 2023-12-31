package com.ssafy.curious.domain.search.repository;

import com.ssafy.curious.domain.search.entity.SearchEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;

public interface SearchRepository extends MongoRepository<SearchEntity,String> {

    @Query("{ 'category1' : ?0, 'category2' : ?1, 'createdAt' : { $gte: ?2, $lte: ?3 }, 'keywords': { $elemMatch: { 'keyword': ?4 } } }")
    List<SearchEntity> findCustomByCategoryAndCreatedAtAndKeyword(String category1, String category2, LocalDateTime startDate, LocalDateTime endDate,String keyword);

    @Query("{ 'category1' : ?0, 'category2' : ?1, 'createdAt' : { $gte: ?2, $lte: ?3 } }")
    List<SearchEntity> findCustomByCategoryAndCreatedAt(String category1, String category2, LocalDateTime startDate, LocalDateTime endDate);
    @Query("{ 'category1' : ?0, 'category2' : ?1, 'keywords': { $elemMatch: { 'keyword': ?2 } } }")
    List<SearchEntity> findCustomByCategoryAndKeyword(String category1, String category2,String keyword);
    @Query("{ 'createdAt': { $gte: ?0, $lte: ?1 }, 'keywords': { $elemMatch: { 'keyword': ?2 } } }")
    List<SearchEntity> findCustomByCreatedAtAndKeyword(LocalDateTime startDate, LocalDateTime endDate, String keyword, Pageable pageable);

}
