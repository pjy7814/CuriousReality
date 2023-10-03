package com.ssafy.curious.domain.search.entity;

import com.ssafy.curious.domain.model.Keyword;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Getter // Getter 메서드 자동 생성
@NoArgsConstructor(access = AccessLevel.PUBLIC) // 인자 없는 생성자 자동 생성
@Document(collection = "category_tfidf") // 해당 클래스가 MongoDB의 'article_info'에 매핑
public class HotkeyEntity {
    @Id
    private String id;
    @Field("category1")
    private String category1;

    @Field("created_at")
    private LocalDateTime createdAt;

    @Field("tfidfResult")
    private List<Keyword> tfidfResult;
    @Builder
    public HotkeyEntity(String id,  String category1,LocalDateTime createdAt, List<Keyword> tfidfResult) {
        this.id = id;
        this.category1 = category1;
        this.createdAt = createdAt;
        this.tfidfResult=tfidfResult;


    }
}
