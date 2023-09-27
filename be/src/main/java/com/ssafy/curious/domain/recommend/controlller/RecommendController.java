package com.ssafy.curious.domain.recommend.controlller;

import com.ssafy.curious.domain.article.entity.ArticleInfoEntity;
import com.ssafy.curious.domain.recommend.service.RecommendService;
import com.ssafy.curious.domain.recommend.dto.RecommendArticleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/article")
@RestController
public class RecommendController {

    private final RecommendService recommendService;

    /**
     * 추천 기사 후보군에서 추천 점수를 계산하여 추천 기사 리턴
     */
    // Todo: JWT 적용 후 @AuthenticationPrincipal로 변경
    @GetMapping("/recommend/{memberId}")
    public ResponseEntity<List<RecommendArticleResponse>> recommendArticle(@PathVariable Long memberId) {

        // 추천 기사 리스트 받아오기
        List<Optional<ArticleInfoEntity>> recommendArticleList = recommendService.recommendArticle(memberId);

        // DTO 변환
        List<RecommendArticleResponse> recommendArticleResponseList = new ArrayList<>();
        for (Optional<ArticleInfoEntity> articleInfo : recommendArticleList) {
            log.info(String.valueOf(articleInfo));
            recommendArticleResponseList.add(RecommendArticleResponse.from(articleInfo));
        }

        return ResponseEntity.ok(recommendArticleResponseList);
    }
}
