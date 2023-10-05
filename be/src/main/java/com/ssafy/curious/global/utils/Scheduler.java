package com.ssafy.curious.global.utils;

import com.ssafy.curious.domain.mail.service.MailService;
import com.ssafy.curious.domain.member.entity.MemberEntity;
import com.ssafy.curious.domain.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class Scheduler {
    @Autowired
    private MailService mailService;
    @Autowired
    private MemberRepository memberRepository;

    @Scheduled(cron="0 * * * * ?")
    public void schedule(){
        List<MemberEntity> members = memberRepository.findAll();
        log.info("print size ===== {}", members.size());
        for (MemberEntity member : members) {
            mailService.sendNewsLetter(member.getEmail());
        }
    }
}