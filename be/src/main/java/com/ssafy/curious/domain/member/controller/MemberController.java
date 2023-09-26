package com.ssafy.curious.domain.member.controller;

import com.ssafy.curious.domain.member.dto.MemberRegisterDTO;
import com.ssafy.curious.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<MemberRegisterDTO.Response> register(@RequestBody MemberRegisterDTO.Request dto){
        System.out.println("start");
        System.out.println(dto);
        MemberRegisterDTO.Response response = memberService.register(dto);
        System.out.println(response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(){
        System.out.println("test");
        return new ResponseEntity<>("test", HttpStatus.OK);
    }
}
