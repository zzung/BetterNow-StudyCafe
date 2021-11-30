package com.project.betterNow.domain.repository;

import com.project.betterNow.domain.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByAdminId(String adminId);
}
