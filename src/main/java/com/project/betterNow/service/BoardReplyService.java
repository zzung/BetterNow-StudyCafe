package com.project.betterNow.service;

import com.project.betterNow.domain.entity.Board;
import com.project.betterNow.domain.entity.BoardReply;
import com.project.betterNow.domain.entity.Member;
import com.project.betterNow.domain.repository.BoardReplyRepository;
import com.project.betterNow.domain.repository.BoardRepository;
import com.project.betterNow.domain.repository.MemberRepository;
import com.project.betterNow.dto.model.BoardReplyDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BoardReplyService {

    private final BoardReplyRepository boardReplyRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;


    // 댓글 목록 조회
    @Transactional
    public List<BoardReplyDto> getBoardReplyList(Long boardNum){
        List<BoardReply> boardReplyList = boardReplyRepository.findAll(boardNum);
        List<BoardReplyDto> boardReplyDtoList = new ArrayList<>();

        for(BoardReply boardReply : boardReplyList) {
            BoardReplyDto boardReplyDto = BoardReplyDto.builder()
                    .boReplyNum(boardReply.getBoReplyNum())
                    .boReplyContent(boardReply.getBoReplyContent())
                    .member(boardReply.getMember())
                    .board(boardReply.getBoard())
                    .createDate(boardReply.getCreateDate())
                    .modifyDate(boardReply.getModifyDate())
                    .build();
            boardReplyDtoList.add(boardReplyDto);
        }
        return boardReplyDtoList;
    }

    // 댓글 저장
    @Transactional
    public Long boardReplyWrite(Long boardNum, String loginUserId, String boReplyContent) {
        Member memberEntity = memberRepository.findByMemId(loginUserId).get();
        Board boardEntity = boardRepository.findByBoardNum(boardNum).get();
        BoardReplyDto boardReplyDto = BoardReplyDto.builder()
                    .boReplyContent(boReplyContent)
                    .member(memberEntity)
                    .board(boardEntity)
                    .build();
        return boardReplyRepository.save(boardReplyDto.toEntity()).getBoReplyNum();
    }

    // 댓글 갯수 조회
    @Transactional
    public int getBoardReplyCount(Long boardNum){
        return boardReplyRepository.getBoardReplyCount(boardNum);
    }

    // 댓글 삭제
    @Transactional
    public int deleteBoardReply(Long boReplyNum) {
        return boardReplyRepository.deleteBoardReply(boReplyNum);
    }


}
