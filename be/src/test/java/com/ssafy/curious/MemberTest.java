package com.ssafy.curious;

import com.ssafy.curious.domain.member.entity.Member;
import com.ssafy.curious.domain.model.ArticleCategory;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemberTest {

    @Test
    public void testInitializePreference() {
        // Given
        Member member = Member.builder()
                .id(1L)
                .email("test@test.com")
                .name("Test User")
                .birthday(LocalDate.of(2000, 1, 1))
                .contact("010-1234-5678")
                .build();

        // When & Then
        for (ArticleCategory category : ArticleCategory.values()) {
            assertEquals(5.0f, member.getCategoryPreference().get(category));
        }
    }
}