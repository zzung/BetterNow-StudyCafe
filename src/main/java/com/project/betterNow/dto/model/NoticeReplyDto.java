package com.project.betterNow.dto.model;

import com.project.betterNow.domain.entity.Member;
import com.project.betterNow.domain.entity.Notice;
import com.project.betterNow.domain.entity.NoticeReply;
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
public class NoticeReplyDto {

    @ApiModelProperty(required = true, value = "공지사항 댓글 번호")
    private Long noReplyNum;

    @ApiModelProperty(required = true, value = "공지사항 댓글 내용")
    private String noReplyContent;

    @ApiModelProperty(value = "공지사항 댓글 상태")
    private Character noReplyYn;

    @ApiModelProperty(value = "공지사항 정보")
    private Notice notice;

    @ApiModelProperty(value = "공지사항 댓글 작성자 정보")
    private Member member;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    public NoticeReplyDto(){}

    public NoticeReply toEntity(){
        return NoticeReply.builder()
                .noReplyNum(noReplyNum)
                .noReplyContent(noReplyContent)
                .notice(notice)
                .member(member)
                .build();
    }

    public void setNotice(Notice notice) {this.notice = notice;}
    public void setMember(Member member) {this.member = member;}

    @Builder
    public NoticeReplyDto(Long noReplyNum, String noReplyContent, Notice notice, Member member,
                          LocalDateTime createDate, LocalDateTime modifyDate) {

        this.noReplyNum = noReplyNum;
        this.noReplyContent = noReplyContent;
        this.noReplyYn = 'Y';
        this.notice = notice;
        this.member = member;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }

}
