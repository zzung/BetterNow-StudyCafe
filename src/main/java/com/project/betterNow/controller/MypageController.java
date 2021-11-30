package com.project.betterNow.controller;

import com.project.betterNow.domain.entity.Member;
import com.project.betterNow.dto.model.MemberDto;
import com.project.betterNow.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
@Api(description = "마이페이지 REST API")
public class MypageController {

    private final MemberService memberService;

    @ApiOperation("마이페이지")
    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model) {
        MemberDto loginUser = (MemberDto)session.getAttribute("loginUser");
        model.addAttribute("memId", loginUser.getMemId());

        // 내 정보, 내가 쓴 글 목록 조회하기

        return "member/mypage";
    }

}
