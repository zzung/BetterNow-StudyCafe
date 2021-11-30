package com.project.betterNow.domain.repository;

import com.project.betterNow.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface AccountRepository extends JpaRepository<Member, Long> {
    boolean existsByMemId(String memId);
    boolean existsByPhone(String phone);
}
