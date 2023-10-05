package com.ssafy.curious.domain.mail.service;

import com.ssafy.curious.domain.article.entity.ArticleInfoEntity;
import com.ssafy.curious.domain.mail.dto.MailDTO;
import com.ssafy.curious.domain.mail.dto.NewsLetterArticle;
import com.ssafy.curious.domain.mail.dto.NewsLetterDTO;
import com.ssafy.curious.domain.member.entity.MemberEntity;
import com.ssafy.curious.domain.member.repository.MemberRepository;
import com.ssafy.curious.domain.recommend.service.RecommendService;
import com.ssafy.curious.global.exception.CustomValidationException;
import com.ssafy.curious.global.exception.ErrorCode;
import com.ssafy.curious.security.dto.UserAuth;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {
    GenerateCertPassword generateCertPassword;

    private JavaMailSender mailSender;
    private final MemberRepository memberRepository;
    private final RecommendService recommendService;
    private final TemplateEngine templateEngine;

    @Override
    @Async
    public String mailSimpleSend(MailDTO mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();

        String cert = generateCertPassword.executeGenerate();
        System.out.println(cert);

        String msg = mailDto.getMessage() + "인증번호는 " + cert + "입니다";
        message.setTo(mailDto.getAddress());
        //message.setFrom(MailService.FROM_ADDRESS); // 구글 정책 변경으로 설정한 gmail로 가게됨
        message.setSubject(mailDto.getTitle() + "인증번호 전송 메일입니다");
        message.setText(msg);
        mailSender.send(message);
        return cert;
    }


    @Override
    public NewsLetterDTO.Response sendNewsLetter(String email) {
//        String email = auth.getEmail();

        // [1] 유효성 검사
        MemberEntity member;
        if (memberRepository.findMemberByEmail(email) == null) {
            throw new CustomValidationException(ErrorCode.NO_SUCH_MEMBER);
        } else {
            member = memberRepository.findMemberByEmail(email);
        }

        log.info("member : {}", member.getEmail());
        Long memberId = member.getId();
        String name = member.getName();
        log.info(name);

        List<NewsLetterArticle> articles = new ArrayList<>();
        List<ArticleInfoEntity> recommendList = recommendService.recommendClusterArticle(memberId);
        for (ArticleInfoEntity articleInfoEntity : recommendList) {
            NewsLetterArticle article = new NewsLetterArticle();
            article.setArticle(articleInfoEntity.getArticle());
            article.setTitle(articleInfoEntity.getTitle());
            article.setThumbnail(articleInfoEntity.getThumbnail());
            article.setOriginalUrl(articleInfoEntity.getOriginalUrl());
            articles.add(article);
        }

        String title = name + "님을 위한 뉴스레터입니다.";
        log.info(title);

        MimeMessage message = mailSender.createMimeMessage();
        try {
            message.setFrom(new InternetAddress("no-reply@curious.com", "현실이궁금해!","UTF-8"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(title);
            message.setText(setData(articles, name), "UTF-8", "html");
            mailSender.send(message);
        } catch (MessagingException e) {
            log.info(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return NewsLetterDTO.Response.builder()
                .articles(articles)
                .success(true)
                .build();
    }


    public String setData(List<NewsLetterArticle> articles, String name){
        Context context = new Context();
        context.setVariable("articles",articles);
        context.setVariable("name",name);
        return templateEngine.process("mail", context);
    }
    @Async
    public void justSend(MailDTO mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        mailSender.send(message);
    }
}
