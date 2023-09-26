package com.ssafy.curious.domain.member.service;

import com.ssafy.curious.domain.member.dto.MemberRegisterDTO;

public interface MemberService {
    MemberRegisterDTO.Response register(MemberRegisterDTO.Request dto);
}
