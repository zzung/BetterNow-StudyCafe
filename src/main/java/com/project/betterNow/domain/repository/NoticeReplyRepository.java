package com.project.betterNow.domain.repository;

import com.project.betterNow.domain.entity.BoardReply;
import com.project.betterNow.domain.entity.NoticeReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeReplyRepository extends JpaRepository<NoticeReply, Long> {

    @Query("SELECT n FROM NoticeReply n WHERE n.notice.noticeNum = :noticeNum and n.noReplyYn = 'Y' ORDER BY n.noReplyNum DESC")
    List<NoticeReply> findAll(Long noticeNum);

    @Query("SELECT count(n) FROM NoticeReply n WHERE n.notice.noticeNum = :noticeNum and n.noReplyYn = 'Y'")
    int getNoticeReplyCount(Long noticeNum);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE NoticeReply n SET n.noReplyYn = 'N' WHERE n.noReplyNum = :noReplyNum")
    int deleteNoticeReply(Long noReplyNum);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE NoticeReply n SET n.noReplyContent = :noReplyContent WHERE n.noReplyNum = :noReplyNum and n.notice.noticeNum = :noticeNum")
    int updateNoticeReply(Long noReplyNum, Long noticeNum, String noReplyContent);
}
