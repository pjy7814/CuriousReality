package com.ssafy.curious.domain.member.service;

import com.ssafy.curious.domain.member.dto.MemberDTO;
import com.ssafy.curious.domain.member.repository.MemberRepository;
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
    public MemberDTO.Response update(MemberDTO.Request dto){

//        Optional<MemberEntity> member = memberRepository.findByEmail(email);


        return null;
    }




}
