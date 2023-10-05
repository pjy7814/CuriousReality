package com.ssafy.curious.domain.mail.service;

import com.ssafy.curious.domain.article.entity.ArticleInfoEntity;
import com.ssafy.curious.domain.mail.dto.MailDTO;
import com.ssafy.curious.domain.mail.dto.NewsLetterDTO;
import com.ssafy.curious.domain.member.entity.MemberEntity;
import com.ssafy.curious.domain.member.repository.MemberRepository;
import com.ssafy.curious.domain.model.ArticleMetadata;
import com.ssafy.curious.domain.recommend.service.RecommendService;
import com.ssafy.curious.global.exception.CustomValidationException;
import com.ssafy.curious.global.exception.ErrorCode;
import com.ssafy.curious.security.dto.UserAuth;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {
    GenerateCertPassword generateCertPassword;

    private JavaMailSender mailSender;
    private final MemberRepository memberRepository;
    private final RecommendService recommendService;
    private static final String FROM_ADDRESS = "no_repy@boki.com";

    @Override
    @Async
    public String mailSimpleSend(MailDTO mailDto) {
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

    @Override
    public NewsLetterDTO.Response sendNewsLetter(UserAuth auth){
        String email = auth.getEmail();

        // [1] 유효성 검사
        MemberEntity member;
        if (memberRepository.findMemberByEmail(email) == null){
            throw new CustomValidationException(ErrorCode.NO_SUCH_MEMBER);
        }
        else {
            member = memberRepository.findMemberByEmail(email);
        }

        log.info("member : {}", member.getEmail());
        Long memberId = member.getId();

        List<Optional<ArticleInfoEntity>> recommendList = recommendService.recommendClusterArticle(memberId);

        String title = member.getName().toString() + "님을 위한 뉴스레터입니다.";
        log.info(title);
        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("gogobattle@gmail.com");
        message.setTo(email);
        message.setSubject(title);
        message.setText("하잉~!");
        mailSender.send(message);
        return NewsLetterDTO.Response.builder()
                .recommendList(recommendList)
                .success(true)
                .build();
    }

    @Async
    public void justSend(MailDTO mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        mailSender.send(message);
    }
}
