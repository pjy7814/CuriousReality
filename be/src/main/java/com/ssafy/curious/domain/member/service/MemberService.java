package com.ssafy.curious.domain.member.service;

import com.ssafy.curious.domain.member.dto.MemberDTO;
import org.springframework.stereotype.Service;


public interface MemberService {

    MemberDTO.Response update(MemberDTO.Request dto);
}
