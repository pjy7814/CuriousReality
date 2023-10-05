package com.ssafy.curious.domain.recommend.controlller;

import com.ssafy.curious.domain.article.entity.ArticleInfoEntity;
import com.ssafy.curious.domain.member.entity.MemberEntity;
import com.ssafy.curious.domain.member.repository.MemberRepository;
import com.ssafy.curious.domain.recommend.service.RecommendService;
import com.ssafy.curious.domain.recommend.dto.RecommendArticleResponse;
import com.ssafy.curious.security.dto.UserAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/article")
@RestController
public class RecommendController {

    private final RecommendService recommendService;
    private final MemberRepository memberRepository;

    /**
     * 추천 기사 후보군에서 추천 점수를 계산하여 추천 기사 리턴
     */
    @GetMapping("/recommend")
    public ResponseEntity<List<RecommendArticleResponse>> recommendArticle(@AuthenticationPrincipal UserAuth userAuth) {

        // 추천 기사 리스트 받아오기
        Optional<MemberEntity> member  = memberRepository.findByEmail(userAuth.getEmail());
        Long memberId = member.get().getId();
        List<ArticleInfoEntity> recommendClusterArticleList = recommendService.recommendClusterArticle(memberId);
        List<ArticleInfoEntity> recommendCFArticleList = recommendService.recommendCFArticle(memberId);
        List<ArticleInfoEntity> recommendArticleList = Stream.concat(
                        recommendClusterArticleList.stream(), recommendCFArticleList.stream())
                .collect(Collectors.toList());

        log.info("recommendArticles {}", recommendArticleList.toString());

        // DTO 변환
        List<RecommendArticleResponse> recommendArticleResponseList = new ArrayList<>();
        for (ArticleInfoEntity articleInfo : recommendArticleList) {
            log.info(String.valueOf(articleInfo));
            recommendArticleResponseList.add(RecommendArticleResponse.from(articleInfo));
        }

        return ResponseEntity.ok(recommendArticleResponseList);
    }
}
