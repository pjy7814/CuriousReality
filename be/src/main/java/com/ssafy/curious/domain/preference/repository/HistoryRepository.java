package com.ssafy.curious.domain.preference.repository;

import com.ssafy.curious.domain.preference.entity.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {
}
