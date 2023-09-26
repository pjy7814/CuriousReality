package com.ssafy.curious.domain.auth.controller;

import com.ssafy.curious.domain.auth.dto.LoginDTO;
import com.ssafy.curious.domain.auth.dto.LogoutDTO;
import com.ssafy.curious.domain.auth.dto.MemberRegisterDTO;
import com.ssafy.curious.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<MemberRegisterDTO.Response> register(@RequestBody MemberRegisterDTO.Request dto){
        MemberRegisterDTO.Response response = authService.register(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTO.Response> login(@RequestBody LoginDTO.Request dto){
        LoginDTO.Response response = authService.login(dto);
        log.info("컨트롤러 들어온다아앙아아 ");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutDTO.Response> logout(){
//        @AuthenticationPrincipal JwtAuthentication auth
        LogoutDTO.Response response = authService.logout();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
