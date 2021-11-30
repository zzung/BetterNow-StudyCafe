package com.project.betterNow.controller;

import com.google.gson.JsonObject;
import com.project.betterNow.dto.model.BoardDto;
import com.project.betterNow.dto.model.MemberDto;
import com.project.betterNow.service.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@Api(description = "게시글 REST API")
public class BoardController {

    private final BoardService boardService;

    @ApiOperation("게시글 목록 조회")
    @GetMapping("/board")
    public String boardList(Model model) {
        model.addAttribute("boardList", boardService.getBoardList());
        return "/board/boardList";
    }

    @ApiOperation("게시글 상세 조회")
    @GetMapping("/board/{boardNum}")
    public String boardDetail(@PathVariable("boardNum")Long boardNum, Model model) {
        model.addAttribute("boardDto", boardService.getBoardDetail(boardNum));
        return "/board/boardDetail";
    }

    @ApiOperation("게시글 작성 Form")
    @RequestMapping(value = "/board/write", method = RequestMethod.GET)
    public String postWriteForm(Model model) {
        model.addAttribute("boardDto", new BoardDto());
        return "board/writeForm";
    }

    @ApiOperation("게시글 작성")
    @RequestMapping(value = "/board/write", method = RequestMethod.POST)
    public String postWrite(@ModelAttribute("boardDto") @Valid BoardDto boardDto, Model model){

        // 사용자 정보 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String memId = userDetails.getUsername();

        Long boardNum = boardService.savePost(boardDto, memId);
        model.addAttribute("boardDto", boardDto);

        return "redirect:/board/" + boardNum;
    }



}