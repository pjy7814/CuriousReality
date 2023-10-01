package com.ssafy.curious.domain.auth.service;

import com.ssafy.curious.domain.auth.dto.LoginDTO;
import com.ssafy.curious.domain.auth.dto.LogoutDTO;
import com.ssafy.curious.domain.auth.dto.MemberRegisterDTO;
import com.ssafy.curious.domain.auth.dto.ReissueDTO;
import com.ssafy.curious.domain.member.entity.MemberEntity;
import com.ssafy.curious.domain.member.repository.MemberRepository;
import com.ssafy.curious.global.exception.*;
import com.ssafy.curious.global.utils.JwtUtil;
import com.ssafy.curious.global.utils.RegexUtil;
import com.ssafy.curious.security.dto.UserAuth;
import com.ssafy.curious.security.filter.JwtProvider;
import com.ssafy.curious.security.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService{
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtProvider jwtProvider;
    private final RedisService redisService;
    private final JwtUtil jwtUtil;

    @Value("${secret}")
    private String secretKey;

    @Value("{expire}")
    private String expire;
    @Override
    @Transactional
    public MemberRegisterDTO.Response register(MemberRegisterDTO.Request dto) {
        String email = dto.getEmail();
        String contact = dto.getContact();

        // [1] 중복 검사
        // [1-1] 이메일 중복 검사
        Optional<MemberEntity> byEmail = memberRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            throw new AlreadyExistException(ErrorCode.MEMBER_EMAIL_EXISTS);
        }
        // [1-2] 전화번호 중복 검사 - 서비스 완성되면 주석 해제!
//        Optional<MemberEntity> byContact = memberRepository.findByContact(contact);
//        if (byContact.isPresent()) {
//            throw new AlreadyExistException(ErrorCode.MEMBER_CONTACT_EXISTS);
//        }

        // [2] 유효성 검사
        // [2-1] 이메일 형식 검사
        if (!RegexUtil.checkEmailRegex(email))
            throw new CustomValidationException(ErrorCode.INVALID_EMAIL_FORMAT);
        log.info("email format test done");
        // [2-2] 비밀번호 형식 검사
        if (!RegexUtil.checkPasswordRegex(dto.getPassword()))
            throw new CustomValidationException(ErrorCode.INVALID_PASSWORD_FORMAT);
        log.info("pw format test done");
        // [2-3] 이름 형식 검사
        if (!RegexUtil.checkNameRegex(dto.getName()))
            throw new CustomValidationException(ErrorCode.INVALID_NAME_FORMAT);
        log.info("name format test done");
        // [2-4] 전화번호 형식 검사
        if (!RegexUtil.checkContactRegex(dto.getContact()))
            throw new CustomValidationException(ErrorCode.INVALID_CONTACT_FORMAT);
        log.info("contact format test done");
        // [2-5] 비밀번호 일치 검사
        if (!Objects.equals(dto.getPassword(), dto.getPasswordCheck()))
            throw new CustomValidationException(ErrorCode.PASSWORD_NOT_MATCH);
        log.info("password match test done");

        String password = encoder.encode(dto.getPassword());
        log.info("password : {}, encoded : {}", dto.getPassword(), password);

        MemberEntity member = MemberEntity.builder()
                .email(dto.getEmail())
                .password(password)
                .name(dto.getName())
                .contact(dto.getContact())
                .birthday(dto.getBirthday())
                .isSocial(dto.getIsSocial())
                .build();
        log.info("member : " + member.toString());

        MemberEntity savedMember = memberRepository.save(member);

        return MemberRegisterDTO.Response.builder()
                .email(savedMember.getEmail())
                .build();
    }

    @Override
    public LoginDTO.Response login(LoginDTO.Request dto) {

        String email = dto.getEmail();

        // [1] 유효성 검사
        // [1-1] 유저가 없음
        MemberEntity member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.NO_SUCH_MEMBER));

        // [1-2] 비밀번호 틀림
        if(!encoder.matches(dto.getPassword(),member.getPassword())){
            throw new CustomValidationException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        // [2] 로그인 처리
        String accessToken = jwtProvider.createAccessToken(email);
        String refreshToken = jwtProvider.createRefreshToken();

//        log.info("====로그인 처리중 ====");

        redisService.save(email, refreshToken);
        return LoginDTO.Response.builder()
                .success(true)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public LogoutDTO.Response logout() {
        // 컨텍스트에 있는 값 제거
        SecurityContextHolder.clearContext();
        return LogoutDTO.Response.builder()
                .success(true)
                .build();
    }

    @Override
    public ReissueDTO.Response reissue(String email, String accessToken){
        log.info("email : {}, accessToken: {} ", email, accessToken);
        log.info("email : {}", email);
//         refresh token redis 에서 꺼내오기
        String  refreshToken = redisService.getValues(email);

        // 토큰 만료 여부 확인
        try {
            log.info("access Token 만료 여부");
            jwtUtil.validateToken(accessToken);
        } catch (Exception e){
            log.info("refresh Token 만료 여부");
            jwtUtil.validateToken(refreshToken);
        }

        // 유효시간 확인
//        Long expiration = jwtUtil.getExpiration(refreshToken);
//        log.info("time left until expiration : {}", expiration);
//        if (expiration > 0){
//            // 유효성 검사
//            try{
//                jwtUtil.validateToken(refreshToken);
//            } catch (Exception e){
//                log.info("exception : {}", e);
//            }
//        }

        // 유효 시 access token 재발급
        String newAccessToken = jwtProvider.createAccessToken(email);

        return ReissueDTO.Response.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
