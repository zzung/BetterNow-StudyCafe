package com.project.betterNow.controller;

import com.project.betterNow.domain.entity.Member;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@Api(description = "메인 페이지 REST API")
public class MainPageController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/denied")
    public String denied() { return "/member/denied"; }

    @RequestMapping("/facilities")
    public String facilitesInfo() { return "/member/facilitiesInfo";}
}
