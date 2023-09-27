package com.ssafy.curious.domain.member.controller;

import com.ssafy.curious.domain.member.dto.ArticleBookmarkListDTO;
import com.ssafy.curious.domain.member.dto.MemberDTO;
import com.ssafy.curious.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/update")
    public ResponseEntity<MemberDTO.Response> update( @RequestBody MemberDTO.Request dto) {
        MemberDTO.Response response = memberService.update(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/article/bookmark")
    public ResponseEntity<ArticleBookmarkListDTO.Response> getArticleBookmarkList( @RequestBody ArticleBookmarkListDTO.Request dto) {
        ArticleBookmarkListDTO.Response response = memberService.getArticleBookmarkList(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
