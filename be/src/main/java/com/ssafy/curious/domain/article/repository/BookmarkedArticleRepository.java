package com.ssafy.curious.domain.article.repository;

import com.ssafy.curious.domain.article.entity.BookmarkedArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkedArticleRepository extends JpaRepository<BookmarkedArticleEntity, Long> {
    Optional<BookmarkedArticleEntity> findByUrl(String url);
    Optional<List<BookmarkedArticleEntity>> findAllByMember_Email(String email);
    void removeAllByMember_Email(String email);
}
