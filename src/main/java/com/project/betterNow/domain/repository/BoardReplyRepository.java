package com.project.betterNow.domain.repository;

import com.project.betterNow.domain.entity.BoardReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardReplyRepository extends JpaRepository<BoardReply, Long> {

    @Query("SELECT b FROM BoardReply b WHERE b.board.boardNum = :boardNum and b.boReplyYn = 'Y' ORDER BY b.boReplyNum DESC")
    List<BoardReply> findAll(Long boardNum);

    @Query("SELECT count(b) FROM BoardReply b WHERE b.board.boardNum = :boardNum and b.boReplyYn = 'Y'")
    int getBoardReplyCount(Long boardNum);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE BoardReply b SET b.boReplyYn = 'N' WHERE b.boReplyNum = :boReplyNum")
    int deleteBoardReply(Long boReplyNum);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE BoardReply b SET b.boReplyContent = :boReplyContent WHERE b.boReplyNum = :boReplyNum and b.board.boardNum = :boardNum")
    int updateBoardReply(Long boReplyNum, Long boardNum, String boReplyContent);
}
