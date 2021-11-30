package com.project.betterNow.controller;

import com.project.betterNow.domain.entity.Member;
import com.project.betterNow.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@Controller
@Api(description = "아이디, 비밀번호 찾기 REST API")
public class FindIdPwdController {

    private final MemberService memberService;

    @ApiOperation("아이디 비밀번호 찾기 Form")
    @GetMapping(value = "/find/Info")
    public String findIdPwdForm(Model model) {
        return "member/findIdPwdForm";
    }

    @ApiOperation("아이디 찾기")
    @PostMapping("/findId")
    @ResponseBody
    public String findId(@RequestParam Map<String, Object> param) {
        String phone = (String) param.get("phone");
        Member member = memberService.findByPhone(phone);
        String result = "";
        if(member!=null) {
            result = member.getMemId();
        }
        return result;
    }

    @ApiOperation("비밀번호 찾기")
    @PostMapping("/findPwd")
    @ResponseBody
    public String findPwd(@RequestParam Map<String, Object> param) {
        String memId = (String) param.get("memId");
        String phone = (String) param.get("phone");
        Member member = memberService.findPwdByMemIdAndPhone(memId, phone);
        String result = "";
        if(member!=null) {
            result = member.getMemId();
        }
        return result;
    }

    @ApiOperation("비밀번호 변경")
    @PostMapping("/updatePwd")
    @ResponseBody
    public int updateMemPwd(@RequestParam Map<String, Object> param) {
        String memPwd = (String) param.get("memPwd");
        String memId = (String) param.get("memId");
        int result = 0;
        if(memberService.updateMemPwd(memPwd, memId)>0) {
            result = 1;
        }
        return result;
    }

}
