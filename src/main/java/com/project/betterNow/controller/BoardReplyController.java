package com.project.betterNow.controller;

import com.project.betterNow.service.BoardReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@Controller
@Api(description = "게시글 댓글 REST API")
public class BoardReplyController {

    private final BoardReplyService boardReplyService;

    @ApiOperation("게시글 댓글 작성 ajax")
    @PostMapping("/board/reply/write")
    @ResponseBody
    public String boardReplyWrite(@RequestParam Map<String, Object> param){

        String loginUserId = (String) param.get("loginUserId");
        String boReplyContent = (String) param.get("boReplyContent");
        Long boardNum = Long.valueOf(String.valueOf(param.get("boardNum")));

        String result = "";
        if(!loginUserId.equals("") && !boReplyContent.equals("")) {
            boardReplyService.boardReplyWrite(boardNum, loginUserId, boReplyContent);
            result = "success";
        }
        return result;
    }

    @ApiOperation("게시글 댓글 삭제 ajax - 댓글 상태 'N'")
    @RequestMapping(value = "/board/reply/delete/{boReplyNum}", method = RequestMethod.POST)
    @ResponseBody
    public int deleteBoardReply(@PathVariable Long boReplyNum){
        int result = 0;
        if(boardReplyService.deleteBoardReply(boReplyNum) > 0) {
            result++;
        }
        return result;
    }

    @ApiOperation("게시글 댓글 수정 ajax")
    @RequestMapping(value = "/board/reply/edit/{boReplyNum}", method = RequestMethod.POST)
    @ResponseBody
    public int updateBoardReply(@RequestParam Map<String, Object> param) {

        Long boReplyNum = Long.valueOf(String.valueOf(param.get("boReplyNum")));
        Long boardNum = Long.valueOf(String.valueOf(param.get("boardNum")));
        String boReplyContent = (String) param.get("boReplyContent");

        int result = 0;
        if(boardReplyService.updateBoardReply(boReplyNum, boardNum, boReplyContent) > 0) {
            result++;
        }
        return result;
    }


}
