package com.project.betterNow.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "ADMIN")
public class Admin {

    @Id
    @Column(name = "admin_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminNum;

    @Column(name = "admin_id")
    private String adminId;

    @Column(name = "admin_pwd")
    private String adminPwd;

    @Column(name = "auth")
    private String auth;

    @Builder
    public Admin(String adminId, String adminPwd, String auth){
        this.adminId = adminId;
        this.adminPwd = adminPwd;
        this.auth = auth;
    }


}
