package com.ssafy.curious.domain.mail.controller;

import com.ssafy.curious.domain.mail.dto.MailDto;
import com.ssafy.curious.domain.mail.service.MailService;
import io.swagger.models.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    @PostMapping("/mail/mail")
    public ResponseEntity<String> execMail(@RequestBody MailDto mailDto, Model model) {
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

}
