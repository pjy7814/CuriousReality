package com.ssafy.curious.domain.search.repository;

import com.ssafy.curious.domain.search.entity.HotkeyEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface HotkeyRepository extends MongoRepository<HotkeyEntity,String> {

    @Query("{ 'category1' : ?0, 'category2' : ?1 }")
    List<HotkeyEntity> findCustomByCategory(String category1,String category2);


}
