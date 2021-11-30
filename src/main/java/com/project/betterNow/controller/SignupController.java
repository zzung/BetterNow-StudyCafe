package com.project.betterNow.controller;

import com.project.betterNow.config.SignupFormValidator;
import com.project.betterNow.dto.model.MemberDto;
import com.project.betterNow.dto.request.SignupRq;
import com.project.betterNow.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@Api(description = "회원 가입 REST API")
public class SignupController {

    private final MemberService memberService;
    private final SignupFormValidator signupFormValidator;

    @InitBinder("memberDto") // memberDto 라는 데이터를 받을 때 바인더 설정
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signupFormValidator); // memberDto 타입과 매핑되어서 validator 실행됨
    }

    @ApiOperation("회원 가입 Form")
    @RequestMapping(value = "/signupForm", method = RequestMethod.GET)
    public String signupForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "member/signupForm";
    }

    @ApiOperation("회원 가입")
    @RequestMapping(value = "/signupForm", method = RequestMethod.POST)
    public String signup(@Valid @ModelAttribute MemberDto memberDto, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "member/signupForm";
        }
        memberService.signup(memberDto);
        return "redirect:/login";
    }
}
