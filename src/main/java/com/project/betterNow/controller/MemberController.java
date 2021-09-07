package com.project.betterNow.controller;

import com.project.betterNow.dto.model.MemberDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@Api(description = "회원 REST API")
public class MemberController {

//    private final MemberService memberService;

    @ApiOperation("회원 가입 페이지")
    @GetMapping("signUpForm.me")
    public String signupForm() {
        return "/user/signUpForm";
    }

    @ApiOperation("회원 가입")
    @PostMapping("signUp.me")
    public String signup(MemberDto memberDto) {
//        memberService.signup(memberDto);
        return "redirect:/user/loginForm";
    }

    @ApiOperation("로그인 페이지")
    @GetMapping("loginForm.me")
    public String loginForm() {
        return "/user/loginForm";
    }
}
