package com.ssafy.curious.domain.auth.service;

import com.ssafy.curious.domain.auth.dto.LoginDTO;
import com.ssafy.curious.domain.auth.dto.LogoutDTO;
import com.ssafy.curious.domain.auth.dto.RegisterDTO;

public interface AuthService {
    RegisterDTO.Response register(RegisterDTO.Request dto);
    LoginDTO.Response login(LoginDTO.Request dto);
    LogoutDTO.Response logout();

}
