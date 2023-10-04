package com.ssafy.curious.domain.mail.service;

import com.ssafy.curious.domain.mail.dto.MailDto;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {
    GenerateCertPassword generateCertPassword;

    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "no_repy@boki.com";

    @Async
    public String mailSimpleSend(MailDto mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();

        String cert = generateCertPassword.executeGenerate();
        System.out.println(cert);

        String msg = mailDto.getMessage() + "인증번호는 " + cert + "입니다";
        message.setTo(mailDto.getAddress());
        //message.setFrom(MailService.FROM_ADDRESS); // 구글 정책 변경으로 설정한 gmail로 가게됨
        message.setSubject(mailDto.getTitle()+"인증번호 전송 메일입니다");
        message.setText(msg);
        mailSender.send(message);
        return cert;
    }

    @Async
    public void justSend(MailDto mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        mailSender.send(message);
    }
}