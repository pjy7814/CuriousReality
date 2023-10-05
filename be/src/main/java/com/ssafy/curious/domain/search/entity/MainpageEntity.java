package com.ssafy.curious.domain.search.entity;

import com.ssafy.curious.domain.model.Keyword;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.List;

@Getter // Getter 메서드 자동 생성
@NoArgsConstructor(access = AccessLevel.PUBLIC) // 인자 없는 생성자 자동 생성
@Document(collection = "yesterday_tfidf") // 해당 클래스가 MongoDB의 'article_info'에 매핑
public class MainpageEntity {
    @Field("tfidfResult")
    private List<Keyword> tfidfResult;
    @Builder
    public MainpageEntity(List<Keyword> tfidfResult) {
        this.tfidfResult=tfidfResult;
    }
}
