package com.ssafy.curious.domain.auth.repository;

import com.ssafy.curious.domain.auth.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {

    Optional<MemberEntity> findByEmail(String email);
    Optional<MemberEntity> findByContact(String contact);
//    @Query("select m from MemberEntity m where m.email = :email")
//    MemberEntity findByEmail(String email);


}
