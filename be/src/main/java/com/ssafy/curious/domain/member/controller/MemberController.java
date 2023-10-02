package com.ssafy.curious.domain.member.controller;

import com.ssafy.curious.domain.member.dto.ArticleBookmarkListDTO;
import com.ssafy.curious.domain.member.dto.MemberDTO;
import com.ssafy.curious.domain.member.dto.ProfileEditDTO;
import com.ssafy.curious.domain.member.dto.MemberPreferenceDTO;
import com.ssafy.curious.domain.member.service.MemberService;
import com.ssafy.curious.security.dto.UserAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/profile")
    public ResponseEntity<MemberDTO.Response> profile(@AuthenticationPrincipal UserAuth auth){
//        System.out.println("토큰에서 받아온 이메일 : " + auth.getEmail());
        MemberDTO.Response response = memberService.profile(auth);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/profile/edit")
    public ResponseEntity<ProfileEditDTO.Response> editProfile(
            @AuthenticationPrincipal UserAuth auth,
            @RequestBody ProfileEditDTO.Request dto){
        ProfileEditDTO.Response response = memberService.editProfile(auth, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/article/bookmark")
    public ResponseEntity<ArticleBookmarkListDTO.Response> getArticleBookmarkList(@AuthenticationPrincipal UserAuth auth) {
        ArticleBookmarkListDTO.Response response = memberService.getArticleBookmarkList(auth);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/preference")
    public ResponseEntity<MemberPreferenceDTO.Response> getPreference(@AuthenticationPrincipal UserAuth auth) {
        MemberPreferenceDTO.Response response = memberService.getPreference(auth);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
