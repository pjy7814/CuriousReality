package com.ssafy.curious.security.dto;

import lombok.Getter;

@Getter
public class UserAuth {
    private final String email;

    public UserAuth(String email){
        this.email = email;
    }

}
