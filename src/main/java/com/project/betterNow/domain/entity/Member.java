package com.project.betterNow.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "MEMBER")
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memNum;

    @Column(length = 20, nullable = false)
    private String memId;

    @Column(length = 20, nullable = false)
    private String memPwd;

    @Column(length = 11, nullable = false)
    private String phone;

    @Column(length = 1)
    private Character memYn;

    private Long chatRoomNum;

    private Long authNum;

    @Builder
    public Member(Long memNum, String memId, String memPwd, String phone, Character memYn, Long chatRoomNum, Long authNum) {
        this.memNum = memNum;
        this.memId = memId;
        this.memPwd = memPwd;
        this.phone = phone;
        this.memYn = memYn;
        this.chatRoomNum = chatRoomNum;
        this.authNum = authNum;
    }

    @Builder
    public Member(String memId, String memPwd, String phone) {
        this.memId = memId;
        this.memPwd = memPwd;
        this.phone = phone;
    }

}
