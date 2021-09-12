package com.project.betterNow.controller;

import com.project.betterNow.dto.model.MemberDto;
import com.project.betterNow.dto.request.LoginRq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@Api(description = "회원 REST API")
public class MemberController {

//    private final MemberService memberService;

    @ApiOperation("회원 가입 Form")
    @GetMapping("/signupForm")
    public String signupForm() {
        return "/user/signupForm";
    }

    @ApiOperation("회원 가입")
    @PostMapping("/user/signup")
    public String signup(MemberDto memberDto) {
//        memberService.signup(memberDto);
        return "redirect:/user/loginForm";
    }

    @ApiOperation("로그인 Form")
    @GetMapping("/loginForm")
    public String loginForm() {
        return "/user/loginForm";
    }

    @ApiOperation("로그인")
    @PostMapping("/user/login")
    public MemberDto login(@RequestBody @Valid LoginRq request) {
        return null;
    }
}
