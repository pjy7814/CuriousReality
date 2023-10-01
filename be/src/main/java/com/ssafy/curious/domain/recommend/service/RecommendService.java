package com.ssafy.curious.domain.recommend.service;

import com.ssafy.curious.domain.article.entity.ArticleInfoEntity;
import com.ssafy.curious.domain.article.repository.ArticleInfoRepository;
import com.ssafy.curious.domain.member.entity.MemberEntity;
import com.ssafy.curious.domain.member.repository.MemberRepository;
import com.ssafy.curious.domain.model.ArticleCategory;
import com.ssafy.curious.domain.model.ArticlePress;
import com.ssafy.curious.domain.model.ArticleMetadata;
import com.ssafy.curious.domain.recommend.entity.RecommendPoolEntity;
import com.ssafy.curious.domain.model.RecommendScore;
import com.ssafy.curious.domain.recommend.repository.RecommendPoolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecommendService {

    private final RecommendPoolRepository recommendPoolRepository;
    private final MemberRepository memberRepository;
    private final ArticleInfoRepository articleInfoRepository;

    /**
     * 추천 기사 후보군에서 추천 점수를 계산하여 추천 기사 ID 리스트 리턴
     */
    // Todo: 추천 점수 weight 조정, 기사 리턴 개수 구체화
    public List<Optional<ArticleInfoEntity>> recommendArticle(Long memberId) {
        // 추천 기사 후보군 가져오기
        RecommendPoolEntity recommendPoolEntity = recommendPoolRepository.findByMemberId(memberId);
        List<ArticleMetadata> articleList = recommendPoolEntity.getArticleList();

        // 멤버의 카테고리, 언론사 선호도 가져오기
        Optional<MemberEntity> member = memberRepository.findById(memberId);
        Map<ArticleCategory, Integer> categoryPreference = member.get().getCategoryPreference();
        Map<ArticlePress, Integer> pressPreference = member.get().getPressPreference();

        // 추천 점수 계산
        List<RecommendScore> recommendScores = new ArrayList<>();

        for (ArticleMetadata article : articleList) {
            Integer categoryScore = categoryPreference.getOrDefault(article.getCategory1(), 0);
            Integer pressScore = pressPreference.getOrDefault(article.getCompany(), 0);
            Float trendingScore = article.getClusterScale();
            Float totalScore = categoryScore + pressScore + trendingScore;
            recommendScores.add(new RecommendScore(article.getArticleId(), totalScore));
        }

        // 추천 점수 기준 정렬
        recommendScores.sort(Comparator.comparing(RecommendScore::getScore).reversed());

        // 상위 3개 기사 추출
        List<Optional<ArticleInfoEntity>> topArticles = new ArrayList<>();
        int count = 0;
        for (RecommendScore score : recommendScores) {
            if (count < 3) {
                topArticles.add(articleInfoRepository.findById(score.getArticleId()));
                count++;
            } else {
                break;
            }
        }
        log.info("topArticles {}", topArticles.toString());

        return topArticles;
    }
}
