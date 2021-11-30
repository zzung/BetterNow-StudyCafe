package com.project.betterNow.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.io.Serializable;

@Setter
@Getter
public class SignupRq implements Serializable {

    @NotBlank(message = "아이디를 입력해주세요. ")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String memId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=\\\\S+$).{6,12}", message = "비밀번호는 영어 대소문자와 숫자로 포함해서 6~12자리 이내로 입력해주세요.")
    private String memPwd;

    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    @Pattern(regexp = "^(010)[0-9]{4}[0-9]{4}", message = "핸드폰 번호는 숫자로만 입력해주세요.")
    private String phone;
}
