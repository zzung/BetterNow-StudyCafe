package com.project.betterNow.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Api(description = "메인 페이지 REST API")
public class MainPageController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/facilities.ms")
    public String facilitesInfo() { return "/user/facilitiesInfo";}
}
