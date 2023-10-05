package com.ssafy.curious.domain.mail.controller;

import com.ssafy.curious.domain.mail.dto.MailDTO;
import com.ssafy.curious.domain.mail.dto.NewsLetterDTO;
import com.ssafy.curious.domain.mail.service.MailService;
import com.ssafy.curious.security.dto.UserAuth;
import io.swagger.models.Model;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/mail")
@RestController
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    @PostMapping("/mail")
    public ResponseEntity<String> execMail(@RequestBody MailDTO mailDto, Model model) {
        // MailDto에 대한 정보를 담고 /mail/mail로 넘어왔을 때!
        String certification = mailService.mailSimpleSend(mailDto);

        if (certification != null) {
            // 메일 전송이 성공한 경우
            return ResponseEntity.ok(certification);
        } else {
            // 메일 전송이 실패한 경우
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("메일 전송 실패");
        }
    }

    @PostMapping("/newsletter")
    public ResponseEntity<NewsLetterDTO.Response> sendNewsLetter(@AuthenticationPrincipal UserAuth auth){
        NewsLetterDTO.Response response = mailService.sendNewsLetter(auth);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
