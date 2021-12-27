package com.project.betterNow.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "NOTICEREPLY")
@Entity
public class NoticeReply extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no_reply_num")
    private Long noReplyNum;

    @Column(name = "no_reply_content")
    private String noReplyContent;

    @Column(name = "no_reply_yn")
    private Character noReplyYn;

    @ManyToOne(fetch = FetchType.EAGER) // EAGER : 조인할 때 관련 데이터 모두 가져옴
    @JoinColumn(name = "notice_num") // foreign key 컬럼명 설정
    private Notice notice;

    @ManyToOne(fetch = FetchType.EAGER) // EAGER : 조인할 때 관련 데이터 모두 가져옴
    @JoinColumn(name = "mem_num") // foreign key 컬럼명 설정
    private Member member;

    @Builder
    public NoticeReply(Long noReplyNum, String noReplyContent, Notice notice, Member member) {
        this.noReplyNum = noReplyNum;
        this.noReplyContent = noReplyContent;
        this.noReplyYn = 'Y';
        this.notice = notice;
        this.member = member;
    }
}
