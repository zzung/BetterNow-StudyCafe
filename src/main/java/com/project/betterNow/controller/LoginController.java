package com.project.betterNow.controller;

import com.project.betterNow.dto.request.LoginRq;
import com.project.betterNow.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
@Api(description = "로그인 REST API")
public class LoginController {

    private final MemberService memberService;

    @ApiOperation("로그인 Form")
    @RequestMapping("/login")
    public String loginForm() {
        return "/member/loginForm";
    }

    @ApiOperation("로그인")
    @PostMapping("/doLogin")
    public String login(LoginRq request) {
        memberService.loadUserByUsername(request.getLoginId());
        return "redirect:/";
    }

    @ApiOperation("로그아웃")
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request,response, SecurityContextHolder
        .getContext().getAuthentication());
        return "redirect:/";
    }
}
