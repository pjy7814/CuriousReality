package com.ssafy.curious.domain.auth.service;

import com.ssafy.curious.domain.auth.dto.LoginDTO;
import com.ssafy.curious.domain.auth.dto.LogoutDTO;
import com.ssafy.curious.domain.auth.dto.MemberDTO;
import com.ssafy.curious.domain.auth.dto.RegisterDTO;

public interface MemberService {
    RegisterDTO.Response register(RegisterDTO.Request dto);
    LoginDTO.Response login(LoginDTO.Request dto);
    MemberDTO.Response update(MemberDTO.Request dto);
    LogoutDTO.Response logout();

}
