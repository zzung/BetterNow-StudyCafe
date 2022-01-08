package com.project.betterNow.controller;

import com.project.betterNow.service.NoticeReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@Controller
@Api(description = "공지사항 댓글 REST API")
public class NoticeReplyController {

    private final NoticeReplyService noticeReplyService;

    @ApiOperation("공지사항 댓글 작성 ajax")
    @RequestMapping(value = "/notice/reply/write")
    @ResponseBody
    public String noticeReplyWrite(@RequestParam Map<String, Object> param){

        String loginUserId = (String) param.get("loginUserId");
        String noReplyContent = (String) param.get("noReplyContent");
        Long noticeNum = Long.valueOf(String.valueOf(param.get("noticeNum")));

        String result = "";
        if(!loginUserId.equals("") && !noReplyContent.equals("")) {
            noticeReplyService.noticeReplyWrite(noticeNum, loginUserId, noReplyContent);
            result = "success";
        }
        return result;
    }

    @ApiOperation("공지사항 댓글 삭제 ajax - 댓글 상태 'N'")
    @RequestMapping(value = "/notice/reply/delete/{noReplyNum}", method = RequestMethod.POST)
    @ResponseBody
    public int deleteNoticeReply(@PathVariable Long noReplyNum){
        int result = 0;
        if(noticeReplyService.deleteNoticeReply(noReplyNum) > 0) {
            result++;
        }
        return result;
    }

    @ApiOperation("공지사항 댓글 수정 ajax")
    @RequestMapping(value = "/notice/reply/edit/{noReplyNum}", method = RequestMethod.POST)
    @ResponseBody
    public int updateNoticeReply(@RequestParam Map<String, Object> param) {

        Long noReplyNum = Long.valueOf(String.valueOf(param.get("noReplyNum")));
        Long noticeNum = Long.valueOf(String.valueOf(param.get("noticeNum")));
        String noReplyContent = (String) param.get("noReplyContent");

        int result = 0;
        if (noticeReplyService.updateNoticeReply(noReplyNum, noticeNum, noReplyContent) > 0) {
            result ++;
        }
        return result;
    }
}
