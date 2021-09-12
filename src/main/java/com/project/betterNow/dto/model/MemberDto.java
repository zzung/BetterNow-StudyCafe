package com.project.betterNow.dto.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
public class MemberDto {

    @NotNull
    @ApiModelProperty(required = true, value = "회원 번호")
    private Long memNum;

    @NotNull
    @ApiModelProperty(required = true, value = "회원 아이디")
    private String memId;

    @NotNull
    @ApiModelProperty(required = true, value = "회원 비밀번호")
    private String memPwd;

    @NotNull
    @ApiModelProperty(required = true, value = "회원 연락처")
    private String phone;

    @ApiModelProperty(value = "계정 상태")
    private Character memYn;

    @ApiModelProperty(value = "회원 채팅방 번호")
    private Long chatRoomNum;

    @ApiModelProperty(value = "회원 권한 번호")
    private Long authNum;

    public static MemberDto member(Long memNum, String memId, String memPwd, String phone, Character memYn, Long chatRoomNum, Long authNum ) {
       MemberDto mem = new MemberDto();
       mem.memNum = memNum;
       mem.memId = memId;
       mem.memPwd = memPwd;
       mem.phone = phone;
       mem.memYn = memYn;
       mem.chatRoomNum = chatRoomNum;
       mem.authNum = authNum;
       return mem;
    }



}
