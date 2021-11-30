package com.project.betterNow.service;

import com.project.betterNow.domain.entity.Board;
import com.project.betterNow.domain.entity.Member;
import com.project.betterNow.domain.repository.BoardRepository;
import com.project.betterNow.domain.repository.MemberRepository;
import com.project.betterNow.dto.model.BoardDto;
import com.project.betterNow.dto.model.MemberDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BoardService {

    private BoardRepository boardRepository;
    private MemberRepository memberRepository;

    // 게시글 목록 조회
    @Transactional
    public List<BoardDto> getBoardList() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for ( Board boardEntity : boardList) {
            BoardDto boardDTO = BoardDto.builder()
                    .boardNum(boardEntity.getBoardNum())
                    .boardTitle(boardEntity.getBoardTitle())
                    .boardContent(boardEntity.getBoardContent())
                    .member(boardEntity.getMember())
                    .build();

            boardDtoList.add(boardDTO);
        }
        return boardDtoList;
    }

    // 게시글 상세 조회
    @Transactional
    public BoardDto getBoardDetail(Long boardNum) {
        Optional<Board> boardEntityWrapper = boardRepository.findByBoardNum(boardNum);
        Board boardEntity = boardEntityWrapper.get();

        BoardDto boardDto = BoardDto.builder()
                .boardNum(boardEntity.getBoardNum())
                .boardTitle(boardEntity.getBoardTitle())
                .boardContent(boardEntity.getBoardContent())
                .build();
        return boardDto;
    }

    // 게시글 저장
    @Transactional
    public Long savePost(BoardDto boardDto, String memId) {
        Member member = memberRepository.findByMemId(memId).get();
        boardDto.setMember(member);
        return boardRepository.save(boardDto.toEntity()).getBoardNum();
    }





}
