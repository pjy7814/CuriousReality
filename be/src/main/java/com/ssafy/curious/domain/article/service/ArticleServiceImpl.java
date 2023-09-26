package com.ssafy.curious.domain.article.service;

import com.ssafy.curious.domain.article.dto.ArticleLikeDTO;
import com.ssafy.curious.domain.article.entity.LikedArticleEntity;
import com.ssafy.curious.domain.article.repository.LikedArticleRepository;
import com.ssafy.curious.domain.member.entity.MemberEntity;
import com.ssafy.curious.domain.member.repository.MemberRepository;
import com.ssafy.curious.global.exception.ErrorCode;
import com.ssafy.curious.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final LikedArticleRepository likedArticleRepository;
    private final MemberRepository memberRepository;

    /**
     * 유저 기사 스크랩
     * 존재하지 않으면 기사 스크랩 추가, 이미 존재하면 기사 스크랩 삭제
     * @param dto
     */
    @Override
    public void like(ArticleLikeDTO.Request dto) {
        String email = dto.getEmail(); // TODO: temp

        String url = dto.getUrl(); // TODO: 몽고디비에 url 있는지 검사해야하나?

        MemberEntity member = memberRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        Optional<LikedArticleEntity> likedArticleEntityOptional = likedArticleRepository.findByUrl(url);
        if(likedArticleEntityOptional.isPresent()) {
            likedArticleRepository.delete(likedArticleEntityOptional.get());
        } else {
            LikedArticleEntity likedArticle = LikedArticleEntity.builder()
                    .member(member)
                    .url(url)
                    .build();
            likedArticleRepository.save(likedArticle);
        }
    }
}
