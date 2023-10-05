package com.ssafy.curious.domain.search.service;

import com.ssafy.curious.domain.search.entity.HotkeyEntity;
import com.ssafy.curious.domain.search.entity.MainpageEntity;
import com.ssafy.curious.domain.search.repository.MainpageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MainpageService {
    private final MainpageRepository mainpageRepository;

    public List<MainpageEntity> getHotkey(String category1, String category2){
        return mainpageRepository.findAllBy();
    }

}
