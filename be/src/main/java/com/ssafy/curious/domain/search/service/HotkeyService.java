package com.ssafy.curious.domain.search.service;

import com.ssafy.curious.domain.search.entity.HotkeyEntity;
import com.ssafy.curious.domain.search.entity.SearchEntity;
import com.ssafy.curious.domain.search.repository.HotkeyRepository;
import com.ssafy.curious.domain.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HotkeyService {

    private final HotkeyRepository hotkeyRepository;
    public List<HotkeyEntity>getHotkey(String category1,String category2){
        return hotkeyRepository.findCustomByCategory(category1,category2);
    }


}
