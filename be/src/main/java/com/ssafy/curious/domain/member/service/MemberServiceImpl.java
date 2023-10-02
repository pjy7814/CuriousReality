package com.ssafy.curious.domain.member.service;

import com.ssafy.curious.domain.article.entity.ArticleInfoEntity;
import com.ssafy.curious.domain.article.entity.BookmarkedArticleEntity;
import com.ssafy.curious.domain.article.repository.ArticleInfoRepository;
import com.ssafy.curious.domain.article.repository.BookmarkedArticleRepository;
import com.ssafy.curious.domain.member.dto.ArticleBookmarkListDTO;
import com.ssafy.curious.domain.member.dto.MemberDTO;
import com.ssafy.curious.domain.member.dto.ProfileEditDTO;
import com.ssafy.curious.domain.member.entity.MemberEntity;
import com.ssafy.curious.domain.member.repository.MemberRepository;
import com.ssafy.curious.global.exception.CustomValidationException;
import com.ssafy.curious.global.exception.ErrorCode;
import com.ssafy.curious.global.utils.RegexUtil;
import com.ssafy.curious.security.dto.UserAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    private final BCryptPasswordEncoder encoder;
    private final MemberRepository memberRepository;
    private final BookmarkedArticleRepository bookmarkedArticleRepository;
    private final ArticleInfoRepository articleInfoRepository;

    @Override
    public MemberDTO.Response profile(UserAuth auth){
        String email = auth.getEmail();

        MemberEntity member = memberRepository.findMemberByEmail(email);

        return MemberDTO.Response.builder()
                .name(member.getName())
                .contact(member.getContact())
                .categoryPreference(member.getCategoryPreference())
                .build();

    }
    @Transactional
    @Override
    public ProfileEditDTO.Response editProfile(UserAuth auth, ProfileEditDTO.Request dto){

        String email = auth.getEmail();
        String password = null;
        MemberEntity member = null;

        // [1] 존재하는 회원 여부 검사
        if (memberRepository.findMemberByEmail(email) == null){
            throw new CustomValidationException(ErrorCode.MEMBER_NOT_FOUND);
        }
        else {
            member = memberRepository.findMemberByEmail(email);
        }

        // [2] 유효성 검사
        // [2-1] 비밀번호 일치 검사
        if (!encoder.matches(dto.getPassword(),member.getPassword())){
            throw new CustomValidationException(ErrorCode.PASSWORD_NOT_MATCH);
        }
        // 새 비밀번호가 있는 경우
        if (dto.getNewPassword() != null){
            // [2-2] 새 비밀번호 형식 검사
            if (!RegexUtil.checkPasswordRegex(dto.getNewPassword())){
                throw new CustomValidationException(ErrorCode.INVALID_PASSWORD_FORMAT);
            }
            // [2-3] 새 비밀번호 일치 검사
            if (!Objects.equals(dto.getNewPassword(), dto.getCheckNewPassword())){
                throw new CustomValidationException(ErrorCode.PASSWORD_NOT_MATCH);
            }
            password = dto.getNewPassword();
        }
        else {
            password = dto.getPassword();
        }


        member.updateProfile(password, dto.getContact());

        return ProfileEditDTO.Response.builder()
                .success(true)
                .build();
    }
    /**
     * 북마크한 기사 리스트 가져오기
     * @param dto
     * @return
     */
    @Override
    public ArticleBookmarkListDTO.Response getArticleBookmarkList(UserAuth auth) {
        String email = auth.getEmail();

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
