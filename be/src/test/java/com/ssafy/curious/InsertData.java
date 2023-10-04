package com.ssafy.curious;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.curious.domain.article.dto.ArticleBookmarkDTO;
import com.ssafy.curious.domain.article.entity.ArticleInfoEntity;
import com.ssafy.curious.domain.article.repository.ArticleInfoRepository;
import com.ssafy.curious.domain.article.repository.BookmarkedArticleRepository;
import com.ssafy.curious.domain.auth.dto.LoginDTO;
import com.ssafy.curious.domain.model.Keyword;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 실제 데이터베이스에 넣는 데이터
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
@Rollback(value = false)
public class InsertData {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private BookmarkedArticleRepository bookmarkedArticleRepository;

    @Autowired
    private ArticleInfoRepository articleInfoRepository;

    final String memberId = "babo@babo.com";
    final String memberPw = "password1234!";
    String accessToken;

    final JSONParser parser = new JSONParser();
    String articleFilePath = System.getProperty("user.dir") + "/src/test/java/com/ssafy/curious//data/20230901.json";
    List<ArticleInfoEntity> articleInfos = new ArrayList<>();

    @BeforeEach
    public void init() throws Exception {
//        createDummyMember();
        loginMember();
        getArticleFromFile();
    }

    /*
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
    */

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

    public void getArticleFromFile() throws Exception {
        System.out.println("Article File Absolute Path: " + articleFilePath);

        final int limit = 20;
        int count = 0;


        Reader reader = new FileReader(articleFilePath);
        JSONArray jsonArray = (JSONArray) parser.parse(reader);

        for (Object object : jsonArray) {
            if(count == limit) {
                break;
            }
            if (object instanceof JSONObject) {
                JSONObject jsonObj = (JSONObject) object;

                JSONArray preprocessedArray = (JSONArray) jsonObj.get("preprocessed");

                List<Keyword> keywords = new ArrayList<>();
                Random random = new Random();
                for (Object obj : preprocessedArray) {
                    if (obj instanceof String) {
                        String keyword = (String) obj;
                        float randomValue = random.nextInt(101); // 0에서 100 사이의 랜덤 값 생성
                        keywords.add(new Keyword(keyword, randomValue));
                    }
                }
                if (jsonObj.get("original_url").toString().isEmpty()) {
                    continue;
                }

                ArticleInfoEntity articleInfoEntity = ArticleInfoEntity.builder()
                        .originalUrl(jsonObj.get("original_url").toString())
                        .category1(jsonObj.get("category1").toString())
                        .category2(jsonObj.get("category2").toString())
                        .title(jsonObj.get("title").toString())
                        .createdAt(LocalDateTime.parse(jsonObj.get("created_at").toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .thumbnail(jsonObj.get("thumbnail").toString())
                        .company(jsonObj.get("company").toString())
                        .article(jsonObj.get("article").toString())
                        .keywords(keywords)
                        .build();

                articleInfos.add(articleInfoEntity);
            }
            count++;
        }
    }

    @Nested
    @DisplayName("bookmark list")
    public class BookmarkListTest {
        @BeforeEach
        public void init() throws Exception {
//            deleteArticle();
//            createArticle();
            deleteBookmark();
        }

        public void deleteArticle() throws Exception {
            for (ArticleInfoEntity articleInfo : articleInfos) {
                articleInfoRepository.deleteAllByOriginalUrl(articleInfo.getOriginalUrl());
            }
        }

        public void createArticle() {
            articleInfoRepository.saveAll(articleInfos);
        }


        public void deleteBookmark() {
            bookmarkedArticleRepository.removeAllByMember_Email(memberId);
        }

        @Test
        @DisplayName("create bookmark")
        public void createBookmark() throws Exception {
            final int limit = 6;
            int count = 0;
            final int beforeSize = bookmarkedArticleRepository.findAll().size();

            for (ArticleInfoEntity articleInfo : articleInfos) {
                if(count == limit) {
                    break;
                }
                ArticleBookmarkDTO.Request requestDto = ArticleBookmarkDTO.Request
                        .builder()
                        .url(articleInfo.getOriginalUrl())
                        .build();

                mvc.perform(post("/article/bookmark")
                                .header("Authorization", "Bearer " + accessToken)
                                .contentType("application/json;charset=utf-8")
                                .content(mapper.writeValueAsString(requestDto)))
                        // then
                        .andExpect(status().isOk())
                        .andReturn();
                count++;
            }
            assertThat(bookmarkedArticleRepository.findAll().size()).isEqualTo(beforeSize + limit);
        }
    }
}
