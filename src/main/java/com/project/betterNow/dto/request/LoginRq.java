package com.project.betterNow.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoginRq {

    @ApiModelProperty(required = true, value = "아이디")
    @NotEmpty(message = "아이디를 입력해주세요.")
    private String loginId;

    @ApiModelProperty(required = true, value = "비밀번호")
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String loginPwd;
    
}
