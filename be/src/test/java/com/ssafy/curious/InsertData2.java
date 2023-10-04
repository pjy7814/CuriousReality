package com.ssafy.curious;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.curious.domain.article.dto.ArticleBookmarkDTO;
import com.ssafy.curious.domain.article.entity.ArticleInfoEntity;
import com.ssafy.curious.domain.article.repository.ArticleInfoRepository;
import com.ssafy.curious.domain.article.repository.BookmarkedArticleRepository;
import com.ssafy.curious.domain.auth.dto.LoginDTO;
import com.ssafy.curious.domain.auth.dto.MemberRegisterDTO;
import com.ssafy.curious.domain.member.repository.MemberRepository;
import com.ssafy.curious.domain.model.Keyword;
import com.ssafy.curious.domain.preference.dto.SaveHistoryRequest;
import com.ssafy.curious.domain.preference.repository.HistoryRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 실제 데이터베이스에 넣는 데이터
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
@Rollback(value = false)
public class InsertData2 {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private BookmarkedArticleRepository bookmarkedArticleRepository;

    @Autowired
    private ArticleInfoRepository articleInfoRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private HistoryRepository historyRepository;

    HashMap<String, String> loginedMember = new HashMap<>();

    final String memberPw = "password1234!";

    @BeforeEach
    public void init() throws Exception {
        for (int i = 0; i < 20; i++) {
            String email = "testbabo" + i + "@test.com";
            loginedMember.put(email, null);
        }
        createDummyMember();
        loginMember();
    }


    public void createDummyMember() throws Exception {
        System.out.println("=== Start create dummpy member ===");

        for (String email : loginedMember.keySet()) {
            MemberRegisterDTO.Request requestDTO = MemberRegisterDTO.Request.builder()
                    .email(email)
                    .name("멤버테스트")
                    .password(memberPw)
                    .passwordCheck(memberPw)
                    .contact("010-1234-5678")
                    .isSocial(false)
                    .build();
            mvc.perform(post("/auth/register")
                    .contentType("application/json;charset=utf-8")
                    .content(mapper.writeValueAsString(requestDTO)));


        }
    }

    public void loginMember() throws Exception {
        System.out.println("=== Start login member ===");

        for (String email : loginedMember.keySet()) {
            LoginDTO.Request requestDTO = LoginDTO.Request.builder()
                    .email(email)
                    .password(memberPw)
                    .build();

            MvcResult result = mvc.perform(post("/auth/login")
                            .contentType("application/json;charset=utf-8")
                            .content(mapper.writeValueAsString(requestDTO)))
                    .andReturn();

            LoginDTO.Response responseDto = mapper.readValue(result.getResponse().getContentAsString(), LoginDTO.Response.class);
            String accessToken = responseDto.getAccessToken();
            loginedMember.put(email, accessToken);
        }
    }

    @Nested
    @DisplayName("history list")
    public class HistoryTest {

        @Test
        @DisplayName("create history")
        public void createHistory() throws Exception {
            List<ArticleInfoEntity> articleInfos = articleInfoRepository.findAll();
            int historyNum = 20; // per member
            int articleRangeLimit = 100;
            Random random = new Random();

            for (String accessToken : loginedMember.values()) {
                for (int i = 0; i <historyNum; i++) {
                    int randomValue = random.nextInt(articleRangeLimit+1); // 0에서 100 사이의 랜덤 값 생성
                    String url = articleInfos.get(randomValue).getOriginalUrl();
                    SaveHistoryRequest requestDto = SaveHistoryRequest
                            .builder()
                            .articleId(url)
                            .build();

                    mvc.perform(post("/history")
                                    .header("Authorization", "Bearer " + accessToken)
                                    .contentType("application/json;charset=utf-8")
                                    .content(mapper.writeValueAsString(requestDto)))
                            // then
                            .andExpect(status().isOk())
                            .andReturn();
                }
            }
            assertThat(true);
        }
    }
}
