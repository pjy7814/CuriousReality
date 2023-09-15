package com.ssafy.curious.domain.member.controller;

import com.ssafy.curious.domain.member.dto.RegisterDTO;
import com.ssafy.curious.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<RegisterDTO.Response> register(
            @RequestBody RegisterDTO.Request dto){
        RegisterDTO.Response response = memberService.register(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
