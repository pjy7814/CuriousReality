package com.ssafy.curious.domain.preference.service;

import com.ssafy.curious.domain.member.entity.MemberEntity;
import com.ssafy.curious.domain.member.repository.MemberRepository;
import com.ssafy.curious.domain.preference.dto.SaveHistoryRequest;
import com.ssafy.curious.domain.preference.entity.HistoryEntity;
import com.ssafy.curious.domain.preference.repository.HistoryRepository;
import com.ssafy.curious.global.utils.ArticleCategoryConverter;
import com.ssafy.curious.global.utils.ArticlePressConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveHistory(Long memberId, SaveHistoryRequest request) {
        MemberEntity member = memberRepository.findById(memberId).get();
        HistoryEntity historyEntity = HistoryEntity.builder()
                .articleId(request.getArticleId())
                .category1(ArticleCategoryConverter.convertRawCategory(request.getCategory1Code()))
                .company(ArticlePressConverter.convertRawPress(request.getCompanyCode()))
                .member(member)
                .build();
        historyRepository.save(historyEntity);
    }
}
