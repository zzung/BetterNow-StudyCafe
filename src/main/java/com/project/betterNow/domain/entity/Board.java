package com.project.betterNow.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Table(name = "BOARD")
@Entity
public class Board extends BaseTimeEntity{

    @Id
    @Column(name = "board_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardNum;

    @Column(name = "board_title")
    private String boardTitle;

    @Lob // 대용량 데이터 저장할 때 사용
    @Column(name = "board_content")
    private String boardContent;

    @Column(name = "board_views")
    private int boardViews;

    @Column(name = "board_yn")
    private Character boardYn;

    @ManyToOne(fetch = FetchType.EAGER) // EAGER : 조인할 때 관련 데이터 모두 가져옴
    @JoinColumn(name = "mem_num") // foreign key 컬럼명 설정
    private Member member;


    @Builder
    public Board(Long boardNum, String boardTitle, String boardContent, Member member) {
        this.boardNum = boardNum;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardViews = 0;
        this.boardYn = 'Y';
        this.member = member;
    }

}
