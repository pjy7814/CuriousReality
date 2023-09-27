package com.ssafy.curious.domain.member.service;

import com.ssafy.curious.domain.member.dto.MemberDTO;
import com.ssafy.curious.domain.member.entity.MemberEntity;
import com.ssafy.curious.domain.member.repository.MemberRepository;
import com.ssafy.curious.security.dto.UserAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public MemberDTO.Response profile(UserAuth auth){
        String email = auth.getEmail();
        log.info(" email : {} ",email);

        MemberEntity member = memberRepository.findMemberByEmail(email);

        log.info ("member 꺼내오기 완료 =====");
        return MemberDTO.Response.builder()
                .name(member.getName())
                .contact(member.getContact())
                .categoryPreference(member.getCategoryPreference())
                .build();

    }




}
