package com.ssafy.curious.domain.preference.service;

import com.ssafy.curious.domain.article.entity.ArticleInfoEntity;
import com.ssafy.curious.domain.article.repository.ArticleInfoRepository;
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

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final MemberRepository memberRepository;
    private final ArticleInfoRepository articleInfoRepository;

    @Transactional
    public void saveHistory(UserAuth userAuth, SaveHistoryRequest request) {
        MemberEntity member = memberRepository.findByEmail(userAuth.getEmail()).get();

        Map<ArticleCategory, Integer> categoryPreference = member.getCategoryPreference();
        Map<ArticlePress, Integer> pressPreference = member.getPressPreference();

        // pressPreference, categoryPreference 업데이트
        List<ArticleInfoEntity> articleInfos = articleInfoRepository.findAllByOriginalUrl(request.getArticleId()).orElse(null);
        if (articleInfos == null || articleInfos.size() == 0) {
            System.out.println("해당 기사 없음!");
            return;
        }
        ArticleInfoEntity articleInfo = articleInfos.get(0);

        if(!ArticleCategoryConverter.isKrCategoryContains(articleInfo.getCategory1()) ||
        !ArticlePressConverter.isKrPressContains(articleInfo.getCompany())) {
            System.out.println("해당 기사 취급안함!");
            return;
        }

        ArticleCategory category = ArticleCategoryConverter.convertKrToEnumCategory(articleInfo.getCategory1());
        ArticlePress company = ArticlePressConverter.convertKrToEnumPress(articleInfo.getCompany());

        Integer categoryCount = categoryPreference.get(category);
        categoryPreference.put(category, categoryCount + 1);
        Integer companyCount = pressPreference.get(company);
        pressPreference.put(company, companyCount + 1);

        // history 저장
        HistoryEntity historyEntity = HistoryEntity.builder()
                .articleId(request.getArticleId())
                .category1(category)
                .company(company)
                .member(member)
                .build();
        historyRepository.save(historyEntity);
    }
}
