package com.project.betterNow.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "BOARDREPLY")
@Entity
public class BoardReply extends BaseTimeEntity{

    @Id
    @Column(name = "bo_reply_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boReplyNum;

    @Column(name = "bo_reply_content")
    private String boReplyContent;

    @Column(name = "bo_reply_yn")
    private Character boReplyYn;

    @ManyToOne(fetch = FetchType.EAGER) // EAGER : 조인할 때 관련 데이터 모두 가져옴
    @JoinColumn(name = "board_num") // foreign key 컬럼명 설정
    private Board board;

    @ManyToOne(fetch = FetchType.EAGER) // EAGER : 조인할 때 관련 데이터 모두 가져옴
    @JoinColumn(name = "mem_num") // foreign key 컬럼명 설정
    private Member member;

    @Builder
    public BoardReply(Long boReplyNum, String boReplyContent, Board board, Member member) {
        this.boReplyNum = boReplyNum;
        this.boReplyContent = boReplyContent;
        this.boReplyYn = 'Y';
        this.board = board;
        this.member = member;
    }

}
