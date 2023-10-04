package com.ssafy.curious.domain.auth.service;

import com.ssafy.curious.domain.auth.dto.*;
import com.ssafy.curious.security.dto.UserAuth;

public interface AuthService {
    MemberRegisterDTO.Response register(MemberRegisterDTO.Request dto);
    LoginDTO.Response login(LoginDTO.Request dto);
    LogoutDTO.Response logout();
    ReissueDTO.Response reissue(String email, String accessToken);
    MemberDeleteDTO.Response delete(UserAuth auth, MemberDeleteDTO.Request dto);
}
