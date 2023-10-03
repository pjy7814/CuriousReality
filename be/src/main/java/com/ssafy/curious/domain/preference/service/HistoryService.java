package com.ssafy.curious.domain.preference.service;

import com.ssafy.curious.domain.member.entity.MemberEntity;
import com.ssafy.curious.domain.member.repository.MemberRepository;
import com.ssafy.curious.domain.model.ArticleCategory;
import com.ssafy.curious.domain.model.ArticlePress;
import com.ssafy.curious.domain.preference.dto.SaveHistoryRequest;
import com.ssafy.curious.domain.preference.entity.HistoryEntity;
import com.ssafy.curious.domain.preference.repository.HistoryRepository;
import com.ssafy.curious.global.utils.ArticleCategoryConverter;
import com.ssafy.curious.global.utils.ArticlePressConverter;
import com.ssafy.curious.security.dto.UserAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveHistory(UserAuth userAuth, SaveHistoryRequest request) {
        MemberEntity member = memberRepository.findByEmail(userAuth.getEmail()).get();
        Map<ArticleCategory, Integer> categoryPreference = member.getCategoryPreference();
        Map<ArticlePress, Integer> pressPreference = member.getPressPreference();

        // pressPreference, categoryPreference 업데이트
        ArticleCategory category = ArticleCategoryConverter.convertEnumCategory(request.getCategory1Code());
        ArticlePress company = ArticlePressConverter.convertRawPress(request.getCompanyCode());

        Integer categoryCount = categoryPreference.get(category);
        categoryPreference.put(category, categoryCount + 1);
        Integer companyCount = pressPreference.get(company);
        pressPreference.put(company, companyCount + 1);

        // history 저장
        HistoryEntity historyEntity = HistoryEntity.builder()
                .articleId(request.getArticleId())
                .category1(ArticleCategoryConverter.convertEnumCategory(request.getCategory1Code()))
                .company(ArticlePressConverter.convertRawPress(request.getCompanyCode()))
                .member(member)
                .build();
        historyRepository.save(historyEntity);
    }
}
