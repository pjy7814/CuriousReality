package com.ssafy.curious.domain.article.entity;

import com.ssafy.curious.domain.article.repository.ArticleInfoRepository;
import com.ssafy.curious.domain.model.ArticlePress;
import com.ssafy.curious.domain.model.Keyword;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ArticleInfoEntityTest {

    @Autowired
    ArticleInfoRepository articleInfoRepository;

    @Test
    @Rollback(value = false)
    public void createArticleInfoTest() {
        // Given
        Keyword keyword1 = new Keyword();
        keyword1.setKeyword("예시 키워드");
        keyword1.setTfidf(0.9F);
        List<Keyword> keywordList = new ArrayList<>();
        keywordList.add(keyword1);

        ArticleInfoEntity articleInfoEntity = ArticleInfoEntity.builder()
                .originalUrl("http://hstest1.com")
                .category1("정치")
                .category2("대통령실")
                .title("인도네시아 동포 어쩌구")
                .createdAt(LocalDateTime.parse("2023-09-05T22:43:46"))
                .thumbnail("thumbnail")
                .company("중앙일보")
                .article("블라블라")
                .keywords(keywordList)
                .build();

        // When & Then
        articleInfoRepository.save(articleInfoEntity);
    }
}