package com.project.betterNow.dto.model;

import com.project.betterNow.domain.entity.Board;
import com.project.betterNow.domain.entity.BoardReply;
import com.project.betterNow.domain.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class BoardReplyDto {

    @ApiModelProperty(required = true, value = "게시글 댓글 번호")
    private Long boReplyNum;

    @ApiModelProperty(required = true, value = "게시글 댓글 내용")
    private String boReplyContent;

    @ApiModelProperty(value = "게시글 댓글 상태")
    private Character boReplyYn;

    @ApiModelProperty(value = "게시글 댓글 작성자 정보")
    private Member member;

    @ApiModelProperty(value = "게시글 정보")
    private Board board;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    public BoardReplyDto(){}

    public BoardReply toEntity(){
        return BoardReply.builder()
                .boReplyNum(boReplyNum)
                .boReplyContent(boReplyContent)
                .board(board)
                .member(member)
                .build();
    }

    public void setMember(Member member){
        this.member = member;
    }
    public void setBoard(Board board){
        this.board = board;
    }

    @Builder
    public BoardReplyDto(Long boReplyNum, String boReplyContent, Member member, Board board,
                         LocalDateTime createDate, LocalDateTime modifyDate) {

        this.boReplyNum = boReplyNum;
        this.boReplyContent = boReplyContent;
        this.boReplyYn = 'Y';
        this.member = member;
        this.board = board;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }


}
