package com.project.betterNow.dto.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
public class MemberDto {

    @NotNull
    @ApiModelProperty(required = true, value = "회원 번호")
    private int memNum;

    @NotNull
    @ApiModelProperty(required = true, value = "회원 아이디")
    private String memId;

    @NotNull
    @ApiModelProperty(required = true, value = "회원 비밀번호")
    private String memPwd;

    @NotNull
    @ApiModelProperty(required = true, value = "회원 연락처")
    private String phone;



}
