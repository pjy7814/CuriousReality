package com.ssafy.curious.domain.recommend.service;

import com.ssafy.curious.domain.article.entity.ArticleInfoEntity;
import com.ssafy.curious.domain.article.repository.ArticleInfoRepository;
import com.ssafy.curious.domain.member.entity.MemberEntity;
import com.ssafy.curious.domain.member.repository.MemberRepository;
import com.ssafy.curious.domain.model.ArticleCategory;
import com.ssafy.curious.domain.model.ArticlePress;
import com.ssafy.curious.domain.model.ArticleMetadata;
import com.ssafy.curious.domain.recommend.entity.RecommendPoolCFEntity;
import com.ssafy.curious.domain.recommend.entity.RecommendPoolClusterEntity;
import com.ssafy.curious.domain.model.RecommendScore;
import com.ssafy.curious.domain.recommend.repository.RecommendPoolClusterRepository;
import com.ssafy.curious.domain.recommend.repository.RecommendPoolCFRepository;
import com.ssafy.curious.global.utils.ArticleCategoryConverter;
import com.ssafy.curious.global.utils.ArticlePressConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecommendService {

    private final RecommendPoolClusterRepository recommendPoolClusterRepository;
    private final RecommendPoolCFRepository recommendPoolCFRepository;
    private final MemberRepository memberRepository;
    private final ArticleInfoRepository articleInfoRepository;

    LocalDate today = LocalDate.now();
    LocalDateTime startTime = today.atStartOfDay();
    LocalDateTime endTime = today.atTime(23, 59, 59);

    /**
     * 클러스터링 기반 추천 기사 후보군에서 추천 점수를 계산하여 추천 기사 ID 리스트 리턴
     */
    // Todo: 추천 점수 weight 조정, 기사 리턴 개수 구체화
    public List<ArticleInfoEntity> recommendClusterArticle(Long memberId) {
        // 추천 기사 후보군 가져오기
        Optional<RecommendPoolClusterEntity> recommendPoolCluster = recommendPoolClusterRepository.findByCreatedAtBetween(startTime, endTime);
        List<ArticleMetadata> articleList = recommendPoolCluster.get().getArticleList();
        log.info("poolCluster {}", articleList.toString());

        // 멤버의 카테고리, 언론사 선호도 가져오기
        Optional<MemberEntity> member = memberRepository.findById(memberId);
        Map<ArticleCategory, Integer> categoryPreference = member.get().getCategoryPreference();
        Map<ArticlePress, Integer> pressPreference = member.get().getPressPreference();

        // 각 카테고리 별로 가장 높은 점수를 저장하기 위한 맵
        Map<ArticleCategory, RecommendScore> bestScoresByCategory = new HashMap<>();

        for (ArticleMetadata article : articleList) {
            Integer categoryScore = categoryPreference.getOrDefault(ArticleCategoryConverter.convertKrToEnumCategory(article.getCategory1()), -100);
            Integer pressScore = pressPreference.getOrDefault(ArticlePressConverter.convertKrToEnumPress(article.getCompany()), -100);
            Integer totalScore = categoryScore + pressScore;

            RecommendScore currentScore = bestScoresByCategory.get(article.getCategory1());

            // 현재 카테고리의 최고 점수가 없거나 현재 점수가 더 높을 때 업데이트
            if (currentScore == null || totalScore > currentScore.getScore()) {
                bestScoresByCategory.put(ArticleCategoryConverter.convertKrToEnumCategory(article.getCategory1()), new RecommendScore(article.getOriginal_url(), totalScore));
            }
        }

        // 추천 점수 기준 정렬
        List<RecommendScore> bestArticleList = new ArrayList<>(bestScoresByCategory.values());
        log.info("bestClusterArticleList {}", bestArticleList.toString());

        // 상위 5개 기사 추출
        List<ArticleInfoEntity> topArticles = new ArrayList<>();
        int count = 0;
        for (RecommendScore score : bestArticleList) {
            if (count < 5) {
                // 같은 Url을 가진 여러 기사가 있는 경우 제일 첫번째 기사만 가져온다
                Optional<List<ArticleInfoEntity>> articleInfoList = articleInfoRepository.findAllByOriginalUrl(score.getArticleUrl())
                        .stream().findFirst();
                // 조회한 기사가 존재할 때만 추가
                if (articleInfoList.isPresent() && !articleInfoList.get().isEmpty()) {
                    ArticleInfoEntity articleInfo = articleInfoList.get().get(0);
                    topArticles.add(articleInfo);
                }
                log.info("cluster article urls {}", score.getArticleUrl());
                count++;
            } else {
                break;
            }
        }
        log.info("topArticles {}", topArticles.toString());

        return topArticles;
    }

    /**
     * 협업 필터링 기반 추천 기사 후보군에서 추천 점수를 계산하여 추천 기사 ID 리스트 리턴
     */
    public List<ArticleInfoEntity> recommendCFArticle(Long memberId) {
        // 추천 기사 후보군 가져오기
        Optional<RecommendPoolCFEntity> recommendPoolCF = recommendPoolCFRepository.findByCreatedAtBetweenAndMemberId(startTime, endTime, memberId);
        // 협업 필터링 추천 후보군이 비어있을 경우 추천하지 않음
        if(recommendPoolCF.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> articleUrlList = recommendPoolCF.get().getArticleList();
        log.info("articleUrlList {}", articleUrlList.toString());

        // 상위 5개 기사 추출
        List<ArticleInfoEntity> topArticles = new ArrayList<>();
        int count = 0;
        for (String articleUrl : articleUrlList) {
            if (count < 5) {
                // 같은 Url을 가진 여러 기사가 있는 경우 제일 첫번째 기사만 가져온다
                Optional<List<ArticleInfoEntity>> articleInfoList = articleInfoRepository.findAllByOriginalUrl(articleUrl)
                        .stream().findFirst();
                // 기사가 있는 경우에만 처리
                if (articleInfoList.isPresent() && !articleInfoList.get().isEmpty()) {
                    ArticleInfoEntity articleInfo = articleInfoList.get().get(0);
                    topArticles.add(articleInfo);
                }
                count++;
            } else {
                break;
            }
        }
        log.info("topCFArticles {}", topArticles.toString());

        return topArticles;
    }
}
