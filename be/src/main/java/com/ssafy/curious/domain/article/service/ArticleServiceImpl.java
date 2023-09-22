package com.ssafy.curious.domain.article.service;

import com.ssafy.curious.domain.article.dto.LikeDTO;
import com.ssafy.curious.domain.article.entity.LikedArticleEntity;
import com.ssafy.curious.domain.article.repository.LikedArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final LikedArticleRepository likedArticleRepository;

    @Override
    public void like(LikeDTO.Request dto) {
        String email = dto.getEmail(); // TODO: temp

        String url = dto.getUrl(); // TODO: 몽고디비에 url 있는지 검사해야하나?

        LikedArticleEntity likedArticle = LikedArticleEntity.builder()
                .url(url)
                .build();

        likedArticleRepository.save(likedArticle);
    }
}
