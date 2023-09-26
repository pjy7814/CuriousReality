package com.ssafy.curious.domain.member.service;

import com.ssafy.curious.domain.member.dto.MemberRegisterDTO;
import com.ssafy.curious.domain.member.entity.MemberEntity;
import com.ssafy.curious.domain.member.repository.MemberRepository;
import com.ssafy.curious.global.exception.AlreadyExistException;
import com.ssafy.curious.global.exception.CustomValidationException;
import com.ssafy.curious.global.exception.ErrorCode;
import com.ssafy.curious.global.utils.RegexUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

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

        MemberEntity member = MemberEntity.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
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
}
