package com.ssafy.curious.domain.mail.service;

import com.ssafy.curious.domain.mail.dto.MailDTO;
import com.ssafy.curious.domain.mail.dto.NewsLetterDTO;
import com.ssafy.curious.security.dto.UserAuth;

public interface MailService {
    String mailSimpleSend(MailDTO mailDto);
    NewsLetterDTO.Response sendNewsLetter(String email);

}
