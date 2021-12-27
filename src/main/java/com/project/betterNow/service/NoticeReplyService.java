package com.project.betterNow.service;

import com.project.betterNow.domain.entity.Member;
import com.project.betterNow.domain.entity.Notice;
import com.project.betterNow.domain.entity.NoticeReply;
import com.project.betterNow.domain.repository.MemberRepository;
import com.project.betterNow.domain.repository.NoticeReplyRepository;
import com.project.betterNow.domain.repository.NoticeRepository;
import com.project.betterNow.dto.model.NoticeReplyDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NoticeReplyService {

    private final NoticeReplyRepository noticeReplyRepository;
    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;


    // 댓글 목록 조회
    @Transactional
    public List<NoticeReplyDto> getNoticeReplyList(Long noticeNum) {
        List<NoticeReply> noticeReplyList = noticeReplyRepository.findAll(noticeNum);
        List<NoticeReplyDto> noticeReplyDtoList = new ArrayList<>();

        for(NoticeReply noticeReply : noticeReplyList) {
            NoticeReplyDto noticeReplyDto = NoticeReplyDto.builder()
                    .noReplyNum(noticeReply.getNoReplyNum())
                    .noReplyContent(noticeReply.getNoReplyContent())
                    .notice(noticeReply.getNotice())
                    .member(noticeReply.getMember())
                    .createDate(noticeReply.getCreateDate())
                    .modifyDate(noticeReply.getModifyDate())
                    .build();
            noticeReplyDtoList.add(noticeReplyDto);
        }
        return noticeReplyDtoList;
    }

    // 댓글 저장
    @Transactional
    public Long noticeReplyWrite(Long noticeNum, String loginUser, String noReplyContent) {
        Member memberEntity = memberRepository.findByMemId(loginUser).get();
        Notice noticeEntity = noticeRepository.findByNoticeNum(noticeNum).get();
        NoticeReplyDto noticeReplyDto = NoticeReplyDto.builder()
                .noReplyContent(noReplyContent)
                .member(memberEntity)
                .notice(noticeEntity)
                .build();
        return noticeReplyRepository.save(noticeReplyDto.toEntity()).getNoReplyNum();
    }

    // 댓글 갯수 조회
    @Transactional
    public int getNoticeReplyCount(Long noticeNum) {
        return noticeReplyRepository.getNoticeReplyCount(noticeNum);
    }

    // 댓글 삭제
    @Transactional
    public int deleteNoticeReply(Long noReplyNum) {
        return noticeReplyRepository.deleteNoticeReply(noReplyNum);
    }

}
