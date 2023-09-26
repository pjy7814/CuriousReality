package com.ssafy.curious.domain.article.entity;


import com.ssafy.curious.domain.member.entity.MemberEntity;
import com.ssafy.curious.global.entity.CEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name="liked_article")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikedArticleEntity extends CEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(length = 100)
    private String url;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;
}
