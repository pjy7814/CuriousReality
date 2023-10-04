package com.ssafy.curious.domain.member.entity;

import com.ssafy.curious.domain.article.entity.BookmarkedArticleEntity;
import com.ssafy.curious.domain.model.ArticleCategory;
import com.ssafy.curious.domain.preference.entity.HistoryEntity;
import com.ssafy.curious.domain.model.ArticlePress;
import com.ssafy.curious.global.entity.CUDEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Table(name = "member")
@SQLDelete(sql = "UPDATE member SET deleted_at = now() WHERE member_id = ?")
@Where(clause = "deleted_at is null")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Builder
public class MemberEntity extends CUDEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", updatable = false)
    private Long id;

    @Column(name = "email", length = 255, unique = true, nullable = false)
    private String email;

    @Column(name = "password", length = 255, unique = false, nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "contact")
    private String contact;

    @ColumnDefault("false")
    @Column(name = "is_social", nullable = false)
    private Boolean isSocial;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookmarkedArticleEntity> bookmarkedArticles;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "category_preferences", joinColumns = @JoinColumn(name = "member_id"))
    @MapKeyEnumerated(EnumType.STRING)
    private Map<ArticleCategory, Integer> categoryPreference;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "press_preferences", joinColumns = @JoinColumn(name = "member_id"))
    @MapKeyEnumerated(EnumType.STRING)
    private Map<ArticlePress, Integer> pressPreference;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoryEntity> articleHistory = new ArrayList<>();

    @Builder
    public MemberEntity(Long id, String email, String password,String name, LocalDate birthday, String contact, Boolean isSocial) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.birthday = birthday;
        this.contact = contact;
        this.isSocial = isSocial;
        initializePreference();
    }

    // 카테고리, 언론사 선호도를 5.0F 초기값 설정
    private void initializePreference() {
        this.categoryPreference = new HashMap<>();
        for (ArticleCategory category : ArticleCategory.values()) {
            this.categoryPreference.put(category, 0);
        }

        this.pressPreference = new HashMap<>();
        for (ArticlePress press : ArticlePress.values()) {
            this.pressPreference.put(press, 0);
        }
    }

    public void updateProfile(String password, String contact){
        this.password = password;
        this.contact = contact;
    }
}
