package com.project.betterNow.controller;

import com.project.betterNow.dto.model.MemberDto;
import com.project.betterNow.dto.response.MemberResponseDto;
import com.project.betterNow.service.MemberService;
import com.project.betterNow.dto.request.MemberJoinRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Api(description = "회원 REST API")
public class MemberController {

//    private final MemberService memberService;

    @ApiOperation("회원 가입 페이지")
    @GetMapping("member/signup_form")
    public String signupForm() {
        return "member/signup_page";
    }

    @ApiOperation("회원 가입")
    @PostMapping("member/signup")
    public String signup(MemberDto memberDto) {
//        memberService.signup(memberDto);
        return "redirect:/member/login_page";
    }

    @ApiOperation("로그인 페이지")
    @GetMapping("member/login_form")
    public String loginForm() {
        return "member/login_form";
    }
}
