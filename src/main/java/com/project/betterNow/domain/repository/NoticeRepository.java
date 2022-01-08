package com.project.betterNow.domain.repository;

import com.project.betterNow.domain.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("SELECT n FROM Notice n WHERE n.noticeYn='Y' ORDER BY n.noticeNum DESC")
    List<Notice> findAll();

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Notice n SET n.noticeViews = :noticeViews+1 WHERE n.noticeNum = :noticeNum")
    int addViewCount(Long noticeNum, int noticeViews);

    Optional<Notice> findByNoticeNum(Long noticeNum);

    @Query("UPDATE Notice n SET n.noticeYn = 'N' WHERE n.noticeNum = :noticeNum")
    int deletePost(Long noticeNum);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Notice n SET n.noticeTitle = :noticeTitle, n.noticeContent = :noticeContent WHERE n.noticeNum = :noticeNum")
    int updateNotice(Long noticeNum, String noticeTitle, String noticeContent);

    List<Notice> findByNoticeTitleContainingOrderByNoticeNumDesc(String keyword);
    List<Notice> findByAdminAdminIdContainingOrderByNoticeNumDesc(String keyword);

}
