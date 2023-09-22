package com.ssafy.curious.domain.auth.controller;

import com.ssafy.curious.domain.auth.dto.LoginDTO;
import com.ssafy.curious.domain.auth.dto.LogoutDTO;
import com.ssafy.curious.domain.auth.dto.MemberDTO;
import com.ssafy.curious.domain.auth.dto.RegisterDTO;
import com.ssafy.curious.domain.auth.service.MemberService;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<RegisterDTO.Response> register(@RequestBody RegisterDTO.Request dto){
        RegisterDTO.Response response = memberService.register(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/update")
    public ResponseEntity<MemberDTO.Response> update(
            @RequestBody MemberDTO.Request dto){
        MemberDTO.Response response = memberService.update(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTO.Response> login(@RequestBody LoginDTO.Request dto){
        LoginDTO.Response response = memberService.login(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutDTO.Response> logout(){
        @AuthenticationPrincipal JwtAuthentication auth
        LogoutDTO.Response response = memberService.logout();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
