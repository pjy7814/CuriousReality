package com.ssafy.curious.domain.member.service;

import com.ssafy.curious.domain.article.entity.ArticleInfoEntity;
import com.ssafy.curious.domain.article.entity.BookmarkedArticleEntity;
import com.ssafy.curious.domain.article.repository.ArticleInfoRepository;
import com.ssafy.curious.domain.article.repository.BookmarkedArticleRepository;
import com.ssafy.curious.domain.member.dto.ArticleBookmarkListDTO;
import com.ssafy.curious.domain.member.dto.MemberDTO;
import com.ssafy.curious.domain.member.entity.MemberEntity;
import com.ssafy.curious.domain.member.repository.MemberRepository;
import com.ssafy.curious.security.dto.UserAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final BookmarkedArticleRepository bookmarkedArticleRepository;
    private final ArticleInfoRepository articleInfoRepository;

    @Override
    public MemberDTO.Response profile(UserAuth auth){
        String email = auth.getEmail();
        log.info(" email : {} ",email);

        MemberEntity member = memberRepository.findMemberByEmail(email);

        log.info ("member 꺼내오기 완료 =====");
        return MemberDTO.Response.builder()
                .name(member.getName())
                .contact(member.getContact())
                .categoryPreference(member.getCategoryPreference())
                .build();

    }

    /**
     * 북마크한 기사 리스트 가져오기
     * @param dto
     * @return
     */
    @Override
    public ArticleBookmarkListDTO.Response getArticleBookmarkList(ArticleBookmarkListDTO.Request dto) {
        String email = dto.getEmail();

        List<BookmarkedArticleEntity> bookmarkedArticles = bookmarkedArticleRepository.findAllByMember_Email(email).orElse(new ArrayList<BookmarkedArticleEntity>());

        ArrayList<ArticleBookmarkListDTO.Response.ArticleInfo> responseDtos = new ArrayList<>();

        for (BookmarkedArticleEntity bookmarkedArticle : bookmarkedArticles) {
            List<ArticleInfoEntity> articleInfos = articleInfoRepository.findAllByOriginalUrl(bookmarkedArticle.getUrl()).orElse(null);
            if (articleInfos == null || articleInfos.size() == 0) {
                continue;
            }
            ArticleInfoEntity articleInfo = articleInfos.get(0);

            responseDtos.add(ArticleBookmarkListDTO.Response.ArticleInfo.builder()
                    .originalUrl(articleInfo.getOriginalUrl())
                    .category1(articleInfo.getCategory1())
                    .category2(articleInfo.getCategory2())
                    .title(articleInfo.getTitle())
                    .createdAt(articleInfo.getCreatedAt())
                    .thumbnail(articleInfo.getThumbnail())
                    .company(articleInfo.getCompany())
                    .article(articleInfo.getArticle())
                    .isBookmarked(true)
                    .build());
        }

        return ArticleBookmarkListDTO.Response.builder()
                .articleInfos(responseDtos)
                .build();
    }


}
