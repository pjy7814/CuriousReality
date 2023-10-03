package com.ssafy.curious.domain.auth.controller;

import com.ssafy.curious.domain.auth.dto.LoginDTO;
import com.ssafy.curious.domain.auth.dto.LogoutDTO;
import com.ssafy.curious.domain.auth.dto.MemberRegisterDTO;
import com.ssafy.curious.domain.auth.dto.ReissueDTO;
import com.ssafy.curious.domain.auth.service.AuthService;
import com.ssafy.curious.security.dto.UserAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutDTO.Response> logout(
            @AuthenticationPrincipal UserAuth auth){
        LogoutDTO.Response response = authService.logout();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/reissue")
    public ResponseEntity<ReissueDTO.Response> reissue(
            ReissueDTO.Request dto){
            ReissueDTO.Response response = authService.reissue(dto.getEmail(), dto.getAccessToken());
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // reissue test
//    @PostMapping("/reissue")
//    public ResponseEntity<ReissueDTO.Response> reissue(
//            @AuthenticationPrincipal UserAuth auth){
//        ReissueDTO.Response response = authService.reissue(auth.getEmail());
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
}
