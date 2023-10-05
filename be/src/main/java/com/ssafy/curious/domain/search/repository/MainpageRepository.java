package com.ssafy.curious.domain.search.repository;

import com.ssafy.curious.domain.search.entity.MainpageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MainpageRepository extends MongoRepository<MainpageEntity,String> {
    List<MainpageEntity> findAllBy();
}
