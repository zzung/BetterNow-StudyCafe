package com.project.betterNow.controller;

import com.project.betterNow.dto.model.NoticeDto;
import com.project.betterNow.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@Api(description = "공지사항 REST API")
public class NoticeController {

    private final NoticeService noticeService;

    @ApiOperation("공지사항 작성")
    @RequestMapping(value = "/notice/write", method = RequestMethod.POST)
    public String noticeWrite(@ModelAttribute("noticeDto") @Valid NoticeDto noticeDto,
                              @AuthenticationPrincipal User user, Model model){

        Long noticeNum = noticeService.savePost(noticeDto, user.getUsername());
        model.addAttribute("noticeDto", noticeDto);

        return "redirect:/notice/" + noticeNum;
    }

    @ApiOperation("공지사항 상세 조회")
    @GetMapping("/notice/{noticeNum}")
    public String noticeDetail(@PathVariable("noticeNum")Long noticeNum, Model model) {

        // 게시글 조회수 증가
        noticeService.addViewCount(noticeService.getNoticeDetail(noticeNum).getNoticeNum(),
                noticeService.getNoticeDetail(noticeNum).getNoticeViews());

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loginUserId = "";

        if(!principal.equals("anonymousUser")) {
            UserDetails userDetails = (UserDetails) principal;
            loginUserId = userDetails.getUsername();
        }

        model.addAttribute("loginUser", loginUserId);
        model.addAttribute("noticeDto", noticeService.getNoticeDetail(noticeNum));

        return "/notice/noticeDetail";
    }



}
