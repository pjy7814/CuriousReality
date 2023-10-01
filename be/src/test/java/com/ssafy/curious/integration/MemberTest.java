package com.ssafy.curious.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.curious.domain.article.dto.ArticleBookmarkDTO;
import com.ssafy.curious.domain.article.entity.ArticleInfoEntity;
import com.ssafy.curious.domain.article.repository.ArticleInfoRepository;
import com.ssafy.curious.domain.article.repository.BookmarkedArticleRepository;
import com.ssafy.curious.domain.auth.dto.LoginDTO;
import com.ssafy.curious.domain.auth.dto.MemberRegisterDTO;
import com.ssafy.curious.domain.member.dto.ArticleBookmarkListDTO;
import com.ssafy.curious.domain.model.ArticlePress;
import com.ssafy.curious.domain.model.Keyword;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class MemberTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private BookmarkedArticleRepository bookmarkedArticleRepository;

    @Autowired
    private ArticleInfoRepository articleInfoRepository;

    final String memberId = "membertest1@test.com";
    final String memberPw = "Qwer1234!";

    String accessToken;

    @BeforeEach
    public void init() throws Exception {
        createDummyMember();
        loginMember();
    }

    public void createDummyMember() throws Exception {
        System.out.println("=== Start create dummpy member ===");
        MemberRegisterDTO.Request requestDTO = MemberRegisterDTO.Request.builder()
                .email(memberId)
                .name("멤버테스트일")
                .password(memberPw)
                .passwordCheck(memberPw)
                .contact("010-1234-5678")
                .isSocial(false)
                .build();
        mvc.perform(post("/auth/register")
                .contentType("application/json;charset=utf-8")
                .content(mapper.writeValueAsString(requestDTO)));
    }

    public void loginMember() throws Exception {
        System.out.println("=== Start login member ===");
        LoginDTO.Request requestDTO = LoginDTO.Request.builder()
                .email(memberId)
                .password(memberPw)
                .build();

        MvcResult result = mvc.perform(post("/auth/login")
                        .contentType("application/json;charset=utf-8")
                        .content(mapper.writeValueAsString(requestDTO)))
                .andReturn();

        LoginDTO.Response responseDto = mapper.readValue(result.getResponse().getContentAsString(), LoginDTO.Response.class);
        accessToken = responseDto.getAccessToken();
    }

    @Nested
    @DisplayName("bookmark list test")
    public class BookmarkListTest {
        @Nested
        @DisplayName("success")
        public class Success {
            @BeforeEach
            public void createDummy() throws Exception {
                deleteDummyArticle();
                createDummyArticle();
                createDummyBookmark();
            }

            @AfterEach
            public void deleteDummy() throws Exception {
                deleteDummyArticle();
            }

            public void createDummyArticle() {
                Keyword keyword1 = new Keyword();
                keyword1.setKeyword("예시 키워드");
                keyword1.setTfidf(0.9F);
                List<Keyword> keywordList = new ArrayList<>();
                keywordList.add(keyword1);

                for (int i = 0; i < 5; i++) {
                    ArticleInfoEntity articleInfoEntity = ArticleInfoEntity.builder()
                            .originalUrl("http://urltest" + i + ".com")
                            .category1("정치")
                            .category2("대통령실")
                            .title("테스트 제목" + i)
                            .createdAt(LocalDateTime.parse("2023-09-05T22:43:46"))
                            .thumbnail("test thumbnail" + i)
                            .company("중앙일보")
                            .article("테스트 내용" + i)
                            .keywords(keywordList)
                            .build();

                    articleInfoRepository.save(articleInfoEntity);
                }
            }

            public void createDummyBookmark() throws Exception {
                for (int i = 0; i < 6; i++) {
                    ArticleBookmarkDTO.Request requestDto = ArticleBookmarkDTO.Request
                            .builder()
                            .email(memberId)
                            .url("http://urltest" + i + ".com")
                            .build();

                    mvc.perform(post("/article/bookmark")
                                    .header("Authorization", "Bearer " + accessToken)
                                    .contentType("application/json;charset=utf-8")
                                    .content(mapper.writeValueAsString(requestDto)))
                            // then
                            .andExpect(status().isOk())
                            .andReturn();
                }
            }
            public void deleteDummyArticle() throws Exception {
                for (int i = 0; i < 5; i++) {
                    articleInfoRepository.deleteAllByOriginalUrl("http://urltest" + i + ".com");
                }
            }

            @Test
            @DisplayName("get list")
            public void successGetListTest() throws Exception {
                // given
                int beforeSize = bookmarkedArticleRepository.findAllByMember_Email(memberId).orElse(new ArrayList<>()).size();

                // when
                MvcResult result = mvc.perform(get("/member/article/bookmark")
                                .contentType("application/json;charset=utf-8")
                                .header("Authorization", "Bearer " + accessToken)
                                .content(mapper.writeValueAsString(ArticleBookmarkListDTO.Request
                                        .builder()
                                        .email(memberId)
                                        .build())))
                        // then
                        .andExpect(status().isOk())
                        .andReturn();

                ArticleBookmarkListDTO.Response responseDto = mapper.readValue(result.getResponse().getContentAsString(), ArticleBookmarkListDTO.Response.class);

                // DB 저장 검증
                assertThat(responseDto.getArticleInfos().size()).isEqualTo(beforeSize - 1); // 스크랩 중 하나는 mongodb에 저장되어 있지 않음
            }
        }
    }
}
