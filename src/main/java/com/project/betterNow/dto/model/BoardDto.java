package com.project.betterNow.dto.model;

import com.project.betterNow.domain.entity.BaseTimeEntity;
import com.project.betterNow.domain.entity.Board;
import com.project.betterNow.domain.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class BoardDto{

    @ApiModelProperty(required = true, value = "게시글 번호")
    private Long boardNum;

    @ApiModelProperty(required = true, value = "게시글 제목")
    private String boardTitle;

    @ApiModelProperty(required = true, value = "게시글 내용")
    private String boardContent;

    @ApiModelProperty(required = true, value = "게시글 조회수")
    private int boardViews;

    @ApiModelProperty(required = true, value = "게시글 상태")
    private Character boardYn;

    @ApiModelProperty(required = true, value = "작성자 정보")
    private Member member;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;


    public BoardDto() {

    }

    public Board toEntity() {
        return Board.builder()
                .boardNum(boardNum)
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .member(member)
                .build();
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Builder
    public BoardDto (Long boardNum, String boardTitle, String boardContent, int boardViews, Member member,
                     LocalDateTime createDate, LocalDateTime modifyDate) {
        this.boardNum = boardNum;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardViews = boardViews;
        this.boardYn = 'Y';
        this.member = member;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }
}
