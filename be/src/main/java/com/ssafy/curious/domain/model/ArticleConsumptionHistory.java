package com.ssafy.curious.domain.model;

import com.ssafy.curious.domain.member.entity.MemberEntity;
import com.ssafy.curious.global.entity.CEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class ArticleConsumptionHistory extends CEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String articleId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

}
