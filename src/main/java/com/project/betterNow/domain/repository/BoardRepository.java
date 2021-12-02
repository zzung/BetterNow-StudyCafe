package com.project.betterNow.domain.repository;

import com.project.betterNow.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAll();
    Optional<Board> findByBoardNum(Long boardNum);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Board b SET b.boardViews = :boardViews+1 WHERE b.boardNum = :boardNum")
    int addViewCount(Long boardNum, int boardViews);
}
