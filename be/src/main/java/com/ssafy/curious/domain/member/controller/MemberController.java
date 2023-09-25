package com.ssafy.curious.domain.member.controller;

import com.ssafy.curious.domain.member.service.MemberService;
import com.ssafy.curious.domain.member.dto.MemberDTO;
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
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/update")
    public ResponseEntity<MemberDTO.Response> update(
            @RequestBody MemberDTO.Request dto){
        MemberDTO.Response response = memberService.update(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
