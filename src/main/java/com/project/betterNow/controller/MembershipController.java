package com.project.betterNow.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Api(description = "멤버쉽 REST API")
public class MembershipController {

    @RequestMapping("/membership.ms")
    public String membershipPage() {
        return "/user/membershipInfo";
    }


}
