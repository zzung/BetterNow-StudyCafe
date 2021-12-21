package com.project.betterNow.dto.model;

import com.project.betterNow.domain.entity.Admin;
import com.project.betterNow.domain.entity.Notice;
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
public class NoticeDto {

    @ApiModelProperty(required = true, value = "공지사항 번호")
    private Long noticeNum;

    @ApiModelProperty(required = true, value = "공지사항 제목")
    private String noticeTitle;

    @ApiModelProperty(required = true, value = "공지사항 내용")
    private String noticeContent;

    @ApiModelProperty(value = "공지사항 조회수")
    private int noticeViews;

    @ApiModelProperty(required = true, value = "공지사항 상태")
    private Character noticeYn;

    @ApiModelProperty(value = "작성자 정보")
    private Admin admin;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;


    public NoticeDto() {}

    public Notice toEntity() {
        return Notice.builder()
                .noticeNum(noticeNum)
                .noticeTitle(noticeTitle)
                .noticeContent(noticeContent)
                .noticeViews(noticeViews)
                .admin(admin)
                .build();
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @Builder
    public NoticeDto (Long noticeNum, String noticeTitle, String noticeContent, int noticeViews, Admin admin,
                     LocalDateTime createDate, LocalDateTime modifyDate) {
        this.noticeNum = noticeNum;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.noticeViews = noticeViews;
        this.noticeYn = 'Y';
        this.admin = admin;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }
}
