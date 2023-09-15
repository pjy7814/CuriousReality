package com.ssafy.curious.domain.member.service;

import com.ssafy.curious.domain.member.dto.RegisterDTO;
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

import javax.xml.bind.ValidationException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public RegisterDTO.Response register(RegisterDTO.Request dto) {

        String email = dto.getEmail();
        String contact = dto.getContact();

        // [1] 중복 검사
        // [1-1] 이메일 중복 검사
        Optional<MemberEntity> byEmail = memberRepository.checkByEmail(email);
        if (byEmail.isPresent())
            throw new AlreadyExistException(ErrorCode.MEMBER_EMAIL_EXISTS);
        // [1-2] 전화번호 중복 검사
        Optional<MemberEntity> byContact = memberRepository.checkByContact(contact);
        if (byContact.isPresent())
            throw new AlreadyExistException(ErrorCode.MEMBER_CONTACT_EXISTS);

        // [2] 유효성 검사
        // [2-1] 이메일 형식 검사
        if(!RegexUtil.checkEmailRegex(email))
            throw new CustomValidationException(ErrorCode.INVALID_EMAIL_FORMAT);
        // [2-2] 비밀번호 형식 검사
        if(!RegexUtil.checkPasswordRegex(dto.getPassword()))
            throw new CustomValidationException(ErrorCode.INVALID_PASSWORD_FORMAT);
        // [2-3] 이름 형식 검사
        if(!RegexUtil.checkNameRegex(dto.getName()))
            throw new CustomValidationException(ErrorCode.INVALID_NAME_FORMAT);
        // [2-4] 전화번호 형식 검사
        if(!RegexUtil.checkContactRegex(dto.getContact()))
            throw new CustomValidationException(ErrorCode.INVALID_CONTACT_FORMAT);

        MemberEntity member = MemberEntity.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .contact(dto.getContact())
                .birthday(dto.getBirthday())
                .isSocial(dto.getIsSocial())
                .build();

        MemberEntity savedMember = memberRepository.save(member);

        return RegisterDTO.Response.builder()
                .email(savedMember.getEmail())
                .build();
    }
}
