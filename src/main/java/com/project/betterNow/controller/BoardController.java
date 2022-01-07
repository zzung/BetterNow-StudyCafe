package com.project.betterNow.controller;

import com.google.gson.JsonObject;
import com.project.betterNow.domain.entity.Member;
import com.project.betterNow.dto.model.BoardDto;
import com.project.betterNow.dto.model.MemberDto;
import com.project.betterNow.dto.model.NoticeDto;
import com.project.betterNow.service.BoardReplyService;
import com.project.betterNow.service.BoardService;
import com.project.betterNow.service.MemberService;
import com.project.betterNow.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@Api(description = "게시글 REST API")
public class BoardController {

    private final BoardService boardService;
    private final NoticeService noticeService;
    private final BoardReplyService boardReplyService;
    private final MemberService memberService;

    @ApiOperation("게시글 목록 조회")
    @GetMapping("/board")
    public String boardList(Model model) {
        model.addAttribute("boardList", boardService.getBoardList());
        model.addAttribute("noticeList", noticeService.getNoticeList());
        return "/board/boardList";
    }

    @ApiOperation("게시글 상세 조회")
    @GetMapping("/board/{boardNum}")
    public String boardDetail(@PathVariable("boardNum")Long boardNum, Model model) {

        // 게시글 조회수+1
        boardService.addViewCount(boardService.getBoardDetail(boardNum).getBoardNum(),
                boardService.getBoardDetail(boardNum).getBoardViews());

        // 로그인 사용자 정보 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loginUserId = "";

        if(!principal.equals("anonymousUser")) {
            UserDetails userDetails = (UserDetails) principal;
            loginUserId = userDetails.getUsername();
        }
        model.addAttribute("loginUser", loginUserId);
        model.addAttribute("boardDto", boardService.getBoardDetail(boardNum));

        // 게시글 댓글 목록 조회
        model.addAttribute("boardReplyList", boardReplyService.getBoardReplyList(boardNum));
        // 게시글 댓글 갯수
        model.addAttribute("boardReplyCount", boardReplyService.getBoardReplyCount(boardNum));

        return "/board/boardDetail";
    }

    @ApiOperation("게시글 작성 Form")
    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String boardWriteForm(@AuthenticationPrincipal User user, Model model) {
        if(user != null) {
            if(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                model.addAttribute("noticeDto", new NoticeDto());
                return "notice/writeForm";
            }
            model.addAttribute("boardDto", new BoardDto());
            return "board/writeForm";
        }
        return "redirect:/";
    }

    @ApiOperation("게시글 작성")
    @RequestMapping(value = "/board/write", method = RequestMethod.POST)
    public String boardWrite(@ModelAttribute("boardDto") @Valid BoardDto boardDto, Model model){

        // 로그인 사용자 정보 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String memId = userDetails.getUsername();

        Long boardNum = boardService.savePost(boardDto, memId);
        model.addAttribute("boardDto", boardDto);

        return "redirect:/board/" + boardNum;
    }

    @ApiOperation("게시글 수정 Form")
    @RequestMapping(value = "/board/edit/{boardNum}", method = RequestMethod.GET)
    public String boardEditForm(@PathVariable Long boardNum, Model model) {
        model.addAttribute("boardDto", boardService.getBoardDetail(boardNum));
        return "board/editForm";
    }

    @ApiOperation("게시글 수정")
    @PostMapping(value = "/board/edit/{boardNum}")
    public String boardEdit(@PathVariable Long boardNum, BoardDto boardDto, String memId) {

        // 로그인 사용자
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String loginUser = userDetails.getUsername();

        if(loginUser.equals(memId)) {
            if(boardService.updateBoard(boardNum, boardDto.getBoardTitle(), boardDto.getBoardContent()) > 0){
                return "redirect:/board/" + boardDto.getBoardNum();
            }
        }

        return "redirect:/board";
    }

    @ApiOperation("게시글 삭제 - 게시글 상태:N")
    @RequestMapping(value = "/board/delete/{boardNum}", method = RequestMethod.POST)
    @ResponseBody
    public int boardDelete( @PathVariable Long boardNum) {
        int result = 0;
        if(boardService.deletePost(boardNum)>0) {
            result++;
        }
        return result;
    }

    @ApiOperation("게시글 검색 기능")
    @RequestMapping(value = "/board/getSearchList", method = RequestMethod.GET)
    @ResponseBody
    public List<BoardDto> getSearchList(@RequestParam("keyword") String keyword){
        List<BoardDto> searchResult = new ArrayList<>();
        searchResult = boardService.getSearchList(keyword);
        return searchResult;
    }


}
