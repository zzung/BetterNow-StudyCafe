package com.project.betterNow.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Table(name = "MEMBER")
@Entity
public class Member {

    @Id
    @Column(name = "mem_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memNum;

    @Column(name = "mem_id", length = 20, nullable = false)
    private String memId;

    @Column(name = "mem_pwd", length = 20, nullable = false)
    private String memPwd;

    @Column(length = 11, nullable = false)
    private String phone;

    @Column(name = "mem_yn", length = 1)
    private Character memYn;

    @Column(name = "auth")
    private String auth;


    @Builder
    public Member (Long memNum, String memId, String memPwd, String phone, Character memYn, String auth) {
        this.memNum = memNum;
        this.memId = memId;
        this.memPwd = memPwd;
        this.phone = phone;
        this.memYn = memYn;
        this.auth = auth;
    }

}
