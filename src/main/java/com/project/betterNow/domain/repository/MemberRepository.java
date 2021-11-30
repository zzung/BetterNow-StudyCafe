package com.project.betterNow.domain.repository;

import com.project.betterNow.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemId(String memId);
    Optional<Member> findByPhone(String phone);
    Optional<Member> findByMemIdAndPhone(String memId, String phone);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.memPwd = :memPwd WHERE m.memId = :memId")
    int updateMemPwd(String memPwd, String memId);

    Member findByMemNum(Long memNum);
}

