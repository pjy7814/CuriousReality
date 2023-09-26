package com.ssafy.curious.domain.member.service;

import com.ssafy.curious.domain.member.dto.MemberDTO;


public interface MemberService {

    MemberDTO.Response update(MemberDTO.Request dto);
}
