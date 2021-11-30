package com.project.betterNow.domain.repository;

import com.project.betterNow.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAll();
    Optional<Board> findByBoardNum(Long boardNum);

}
