package com.ssafy.curious.domain.preference.entity;

import com.ssafy.curious.domain.member.entity.MemberEntity;
import com.ssafy.curious.domain.model.ArticleCategory;
import com.ssafy.curious.domain.model.ArticlePress;
import com.ssafy.curious.global.entity.CEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HistoryEntity extends CEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String articleId;

    private ArticleCategory category1;

    private ArticlePress company;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Builder
    public HistoryEntity (MemberEntity member, String articleId, ArticleCategory category1, String category2, ArticlePress company) {
        this.member = member;
        this.articleId = articleId;
        this.category1 = category1;
        this.company = company;
    }
}
