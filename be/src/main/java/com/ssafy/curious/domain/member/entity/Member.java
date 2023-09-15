package com.ssafy.curious.domain.member.entity;

import com.ssafy.curious.domain.model.ArticleCategory;
import com.ssafy.curious.domain.model.ArticlePress;
import com.ssafy.curious.global.entity.CUDEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends CUDEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", updatable = false)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "contact")
    private String contact;

    @Column(name = "is_social")
    private Boolean isSocial;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "category_preferences", joinColumns = @JoinColumn(name = "member_id"))
    @MapKeyEnumerated(EnumType.STRING)
    private Map<ArticleCategory, Float> categoryPreference;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "press_preferences", joinColumns = @JoinColumn(name = "member_id"))
    @MapKeyEnumerated(EnumType.STRING)
    private Map<ArticlePress, Float> pressPreference;

    @Builder
    public Member(Long id, String email, String name, LocalDate birthday, String contact) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.birthday = birthday;
        this.contact = contact;
        this.isSocial = false;
        initializePreference();
    }

    // 카테고리, 언론사 선호도를 5.0F 초기값 설정
    private void initializePreference() {
        this.categoryPreference = new HashMap<>();
        for (ArticleCategory category : ArticleCategory.values()) {
            this.categoryPreference.put(category, 5.0f);
        }

        this.pressPreference = new HashMap<>();
        for (ArticlePress press : ArticlePress.values()) {
            this.pressPreference.put(press, 5.0f);
        }
    }
}
