package com.project.betterNow.domain.repository;

import com.project.betterNow.domain.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("SELECT n FROM Notice n WHERE n.noticeYn='Y' ORDER BY n.noticeNum DESC")
    List<Notice> findAll();

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Notice n SET n.noticeViews = :noticeViews+1 WHERE n.noticeNum = :noticeNum")
    int addViewCount(Long noticeNum, int noticeViews);



}
