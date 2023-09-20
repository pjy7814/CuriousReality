package com.ssafy.curious.domain.member.service;

import com.ssafy.curious.domain.member.dto.RegisterDTO;

public interface MemberService {
    RegisterDTO.Response register(RegisterDTO.Request dto);
}
