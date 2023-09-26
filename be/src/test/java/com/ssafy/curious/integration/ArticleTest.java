package com.ssafy.curious.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.curious.domain.article.dto.ArticleLikeDTO;
import com.ssafy.curious.domain.article.repository.LikedArticleRepository;
import com.ssafy.curious.domain.member.dto.MemberRegisterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
    private LikedArticleRepository likedArticleRepository;

    final String memberId = "membertest1@test.com";

    @BeforeEach
    public void init() throws Exception {
        createDummyMember();
    }

    public void createDummyMember() throws Exception {
        MemberRegisterDTO.Request requestDTO = MemberRegisterDTO.Request.builder()
                .email(memberId)
                .name("멤버테스트일")
                .password("qwer1234!")
                .contact("010-1234-5678")
                .isSocial(false)
                .build();
        mvc.perform(post("/auth/register")
                .contentType("application/json;charset=utf-8")
                .content(mapper.writeValueAsString(requestDTO)));
    }

    /*
    public String loginPUser(String pUserId, String pUserPw) throws Exception {
        MvcResult result = mvc.perform(post("/pusers/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", pUserId)
                        .param("password", pUserPw))
                .andReturn();

        JwtResponseDto responseDto = mapper.readValue(result.getResponse().getContentAsString(), JwtResponseDto.class);
        return responseDto.getAccessToken();
    }
    */

    @Nested
    @DisplayName("like test")
    public class LikeTest {
        final String url = "https://test.com";

        @Nested
        @DisplayName("success")
        public class Success {
            @BeforeEach
            public void createDummyUrl() throws Exception {
                ArticleLikeDTO.Request requestDto = ArticleLikeDTO.Request
                        .builder()
                        .email(memberId)
                        .url(url)
                        .build();

                mvc.perform(post("/article/like")
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
                int beforeSize = likedArticleRepository.findAll().size();
                ArticleLikeDTO.Request requestDto = ArticleLikeDTO.Request
                        .builder()
                        .email(memberId)
                        .url("https://naver.com")
                        .build();

                // when
                mvc.perform(post("/article/like")
                                .contentType("application/json;charset=utf-8")
                                .content(mapper.writeValueAsString(requestDto)))
                        // then
                        .andExpect(status().isOk())
                        .andReturn();

                // DB 저장 검증
                assertThat(likedArticleRepository.findAll().size()).isEqualTo(beforeSize + 1);

            }

            @Test
            @DisplayName("delete")
            public void successDeleteTest() throws Exception {
                // given
                int beforeSize = likedArticleRepository.findAll().size();
                ArticleLikeDTO.Request requestDto = ArticleLikeDTO.Request
                        .builder()
                        .email(memberId)
                        .url(url)
                        .build();

                // when
                mvc.perform(post("/article/like")
                                .contentType("application/json;charset=utf-8")
                                .content(mapper.writeValueAsString(requestDto)))
                        // then
                        .andExpect(status().isOk())
                        .andReturn();

                // DB 저장 검증
                assertThat(likedArticleRepository.findAll().size()).isEqualTo(beforeSize - 1);

            }
        }
    }
}
