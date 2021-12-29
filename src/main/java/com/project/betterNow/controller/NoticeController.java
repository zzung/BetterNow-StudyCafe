package com.project.betterNow.controller;

import com.project.betterNow.dto.model.BoardDto;
import com.project.betterNow.dto.model.NoticeDto;
import com.project.betterNow.service.NoticeReplyService;
import com.project.betterNow.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final NoticeReplyService noticeReplyService;

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

        // 로그인 사용자 정보 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loginUserId = "";

        if(!principal.equals("anonymousUser")) {
            UserDetails userDetails = (UserDetails) principal;
            loginUserId = userDetails.getUsername();
        }

        model.addAttribute("loginUser", loginUserId);
        model.addAttribute("noticeDto", noticeService.getNoticeDetail(noticeNum));

        // 공지사항 댓글 목록 조회
        model.addAttribute("noticeReplyList", noticeReplyService.getNoticeReplyList(noticeNum));
        // 공지사항 댓글 갯수
        model.addAttribute("noticeReplyCount", noticeReplyService.getNoticeReplyCount(noticeNum));

        return "/notice/noticeDetail";
    }

    @ApiOperation("공지사항 수정 Form")
    @RequestMapping(value = "/notice/edit/{noticeNum}", method = RequestMethod.GET)
    public String noticeEditForm(@PathVariable Long noticeNum, Model model) {
        model.addAttribute("noticeDto", noticeService.getNoticeDetail(noticeNum));
        return "notice/editForm";
    }

    @ApiOperation("공지사항 수정")
    @PutMapping(value = "/notice/edit/{noticeNum}")
    public String noticeEdit(NoticeDto noticeDto, String adminId) {
        noticeService.savePost(noticeDto, adminId);
        return "redirect:/notice/" + noticeDto.getNoticeNum();
    }

    @ApiOperation("게시글 삭제 - 게시글 상태:N")
    @RequestMapping(value = "/notice/delete/{noticeNum}", method = RequestMethod.POST)
    @ResponseBody
    public int noticeDelte( @PathVariable Long noticeNum) {
        int result = 0;
        if(noticeService.deletePost(noticeNum)>0) {
            result++;
        }
        return result;
    }

}