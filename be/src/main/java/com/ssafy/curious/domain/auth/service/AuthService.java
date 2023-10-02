package com.ssafy.curious.domain.auth.service;

import com.ssafy.curious.domain.auth.dto.LoginDTO;
import com.ssafy.curious.domain.auth.dto.LogoutDTO;
import com.ssafy.curious.domain.auth.dto.MemberRegisterDTO;
import com.ssafy.curious.domain.auth.dto.ReissueDTO;

public interface AuthService {
    MemberRegisterDTO.Response register(MemberRegisterDTO.Request dto);
    LoginDTO.Response login(LoginDTO.Request dto);
    LogoutDTO.Response logout();
    ReissueDTO.Response reissue(String email, String accessToken);
}
