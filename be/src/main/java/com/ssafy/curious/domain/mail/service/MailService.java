package com.ssafy.curious.domain.mail.service;

import com.ssafy.curious.domain.mail.dto.MailDTO;

public interface MailService {
    String mailSimpleSend(MailDTO mailDto);

}
