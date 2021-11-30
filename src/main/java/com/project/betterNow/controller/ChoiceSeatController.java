package com.project.betterNow.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
@Api(description = "좌석 선택 페이지 REST API")
public class ChoiceSeatController {

    @ApiOperation("좌석 선택 페이지")
    @GetMapping("/choiceSeat")
    public String choiceSeat() {
        return "member/choiceSeat";
    }
}
