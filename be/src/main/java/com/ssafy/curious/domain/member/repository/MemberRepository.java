package com.ssafy.curious.domain.member.repository;

import com.ssafy.curious.domain.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {

    Optional<MemberEntity> findByEmail(String email);
    Optional<MemberEntity> findByContact(String contact);
    @Query("select m from MemberEntity m where m.email = :email")
    MemberEntity findMemberByEmail(String email);


}
