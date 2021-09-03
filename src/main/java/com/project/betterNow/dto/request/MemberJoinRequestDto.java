package com.project.betterNow.dto.request;

import com.project.betterNow.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberJoinRequestDto {

    private String memId;
    private String memPwd;
    private String phone;

//    @Builder
//    public MemberJoinRequestDto(String memId, String memPwd, String phone) {
//        this.memId = memId;
//        this.memPwd = memPwd;
//        this.phone = phone;
//    }
//
//    public Member toEntity() {
//        return Member.builder()
//                .memId(memId)
//                .memPwd(memPwd)
//                .phone(phone)
//                .build();
//    }

}
