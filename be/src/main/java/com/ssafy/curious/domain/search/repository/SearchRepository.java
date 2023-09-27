package com.ssafy.curious.domain.search.repository;

import com.ssafy.curious.domain.search.entity.SearchEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SearchRepository extends MongoRepository<SearchEntity,String> {

    List<SearchEntity> findAllBy();
}
