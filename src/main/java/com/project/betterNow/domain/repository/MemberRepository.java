package com.project.betterNow.domain.repository;

import com.project.betterNow.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemId(String memId);
}

