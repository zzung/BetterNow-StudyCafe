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
    private Long mem_num;

    @Column(name = "mem_id", length = 20, nullable = false)
    private String memId;

    @Column(name = "mem_pwd", length = 20, nullable = false)
    private String memPwd;

    @Column(length = 11, nullable = false)
    private String phone;

    @Column(name = "mem_yn", length = 1)
    private Character memYn;

    private Long chatRoomNum;

    private Long authNum;

    @Column
    private String picture;

    /** JPA로 데이터베이스로 저장할 때 Enum 값을 어떤 형태로 저장할지 결정함
     * 기본적으로는 int타입으로 저장되지만 분별력이 떨어지기 때문에 String으로 저장되도록 설정
     * */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    @Builder
    public Member(String memId, String picture, Role role) {
        this.memId = memId;
        this.picture = picture;
        this.role = role;
    }

    public Member updateMyPicture(String memId,String picture) {
        this.memId = memId;
        this.picture = picture;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }


}
