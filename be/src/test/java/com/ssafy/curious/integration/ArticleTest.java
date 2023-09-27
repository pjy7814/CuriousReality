package com.ssafy.curious.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.curious.domain.article.dto.ArticleBookmarkDTO;
import com.ssafy.curious.domain.article.repository.BookmarkedArticleRepository;
import com.ssafy.curious.domain.auth.dto.LoginDTO;
import com.ssafy.curious.domain.auth.dto.MemberRegisterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class ArticleTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private BookmarkedArticleRepository bookmarkedArticleRepository;

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
    @DisplayName("bookmark test")
    public class BookmarkTest {
        final String url = "https://test.com";

        @Nested
        @DisplayName("success")
        public class Success {
            @BeforeEach
            public void createDummyUrl() throws Exception {
                ArticleBookmarkDTO.Request requestDto = ArticleBookmarkDTO.Request
                        .builder()
                        .email(memberId)
                        .url(url)
                        .build();

                mvc.perform(post("/article/bookmark")
                                .header("Authorization", "Bearer " + accessToken)
                                .contentType("application/json;charset=utf-8")
                                .content(mapper.writeValueAsString(requestDto)))
                        // then
                        .andExpect(status().isOk())
                        .andReturn();
            }

            @Test
            @DisplayName("create")
            public void successCreateTest() throws Exception {
                // given
                int beforeSize = bookmarkedArticleRepository.findAll().size();
                ArticleBookmarkDTO.Request requestDto = ArticleBookmarkDTO.Request
                        .builder()
                        .email(memberId)
                        .url("https://naver.com")
                        .build();

                // when
                mvc.perform(post("/article/bookmark")
                                .contentType("application/json;charset=utf-8")
                                .header("Authorization", "Bearer " + accessToken)
                                .content(mapper.writeValueAsString(requestDto)))
                        // then
                        .andExpect(status().isOk())
                        .andReturn();

                // DB 저장 검증
                assertThat(bookmarkedArticleRepository.findAll().size()).isEqualTo(beforeSize + 1);

            }

            @Test
            @DisplayName("delete")
            public void successDeleteTest() throws Exception {
                // given
                int beforeSize = bookmarkedArticleRepository.findAll().size();
                ArticleBookmarkDTO.Request requestDto = ArticleBookmarkDTO.Request
                        .builder()
                        .email(memberId)
                        .url(url)
                        .build();

                // when
                mvc.perform(post("/article/bookmark")
                                .contentType("application/json;charset=utf-8")
                                .content(mapper.writeValueAsString(requestDto)))
                        // then
                        .andExpect(status().isOk())
                        .andReturn();

                // DB 저장 검증
                assertThat(bookmarkedArticleRepository.findAll().size()).isEqualTo(beforeSize - 1);

            }
        }
    }
}
