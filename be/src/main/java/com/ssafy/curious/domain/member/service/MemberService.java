package com.ssafy.curious.domain.member.service;

import com.ssafy.curious.domain.member.dto.ArticleBookmarkListDTO;
import com.ssafy.curious.domain.member.dto.MemberDTO;
import com.ssafy.curious.security.dto.UserAuth;


public interface MemberService {

    MemberDTO.Response profile(UserAuth auth);
    ArticleBookmarkListDTO.Response getArticleBookmarkList(UserAuth auth);
}
