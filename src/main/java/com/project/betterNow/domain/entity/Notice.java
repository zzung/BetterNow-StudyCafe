package com.project.betterNow.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "NOTICE")
public class Notice extends BaseTimeEntity{

    @Id
    @Column(name = "notice_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeNum;

    @Column(name = "notice_title")
    private String noticeTitle;

    @Lob
    @Column(name = "notice_content")
    private String noticeContent;

    @Column(name = "notice_yn")
    private Character noticeYn;

    @Column(name = "notice_views")
    private int noticeViews;

    @ManyToOne(fetch = FetchType.EAGER) // EAGER : 조인할 때 관련 데이터 모두 가져옴
    @JoinColumn(name = "admin_num") // foreign key 컬럼명 설정
    private Admin admin;


    @Builder
    public Notice(Long noticeNum, String noticeTitle, String noticeContent, int noticeViews, Admin admin) {
        this.noticeNum = noticeNum;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.noticeViews = noticeViews;
        this.noticeYn = 'Y';
        this.admin = admin;
    }
}
