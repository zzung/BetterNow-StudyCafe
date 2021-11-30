package com.project.betterNow.config;

import com.project.betterNow.domain.repository.AccountRepository;
import com.project.betterNow.dto.model.MemberDto;
import com.project.betterNow.dto.request.SignupRq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignupFormValidator implements Validator {

    private final AccountRepository accountRepository;

    // MemberDto 타입의 인스턴스 검사
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(MemberDto.class);
    }

    // Id 중복 검사
    @Override
    public void validate(Object target, Errors errors) {
        MemberDto memberDto = (MemberDto) target;
        if(accountRepository.existsByMemId(memberDto.getMemId())) {
            errors.rejectValue("memId", "invalid.memId", new Object[]{memberDto.getMemId()}, "이미 사용중인 아이디 입니다.");
        }
        if(accountRepository.existsByPhone(memberDto.getPhone())) {
            errors.rejectValue("phone", "invalid.phone", new Object[]{memberDto.getPhone()}, "이미 사용중인 휴대폰번호 입니다.");
        }
    }
}
