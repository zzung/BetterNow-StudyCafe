package com.project.betterNow.service;

import com.project.betterNow.domain.entity.Admin;
import com.project.betterNow.domain.entity.Notice;
import com.project.betterNow.domain.repository.AdminRepository;
import com.project.betterNow.domain.repository.NoticeRepository;
import com.project.betterNow.dto.model.NoticeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NoticeService {

    private NoticeRepository noticeRepository;
    private AdminRepository adminRepository;

    // 공지사항 목록 조회
    @Transactional
    public List<NoticeDto> getNoticeList(){
        List<Notice> noticeList = noticeRepository.findAll();
        List<NoticeDto> noticeDtoList = new ArrayList<>();

        for ( Notice noticeEntity : noticeList) {
            NoticeDto noticeDTO = NoticeDto.builder()
                    .noticeNum(noticeEntity.getNoticeNum())
                    .noticeTitle(noticeEntity.getNoticeTitle())
                    .noticeContent(noticeEntity.getNoticeContent())
                    .noticeViews(noticeEntity.getNoticeViews())
                    .admin(noticeEntity.getAdmin())
                    .createDate(noticeEntity.getCreateDate())
                    .modifyDate(noticeEntity.getModifyDate())
                    .build();

            noticeDtoList.add(noticeDTO);
        }
        return noticeDtoList;
    }

    // 공지사항 작성
    public Long savePost(NoticeDto noticeDto, String adminId) {
        Admin admin = adminRepository.findByAdminId(adminId).get();
        noticeDto.setAdmin(admin);

        return noticeRepository.save(noticeDto.toEntity()).getNoticeNum();
    }

    // 공지사항 상세 조회
    @Transactional
    public NoticeDto getNoticeDetail(Long noticeNum){
        Optional<Notice> noticeWrapper = noticeRepository.findById(noticeNum);
        Notice noticeEntity = noticeWrapper.get();

        NoticeDto noticeDto = NoticeDto.builder()
                .noticeNum(noticeEntity.getNoticeNum())
                .noticeTitle(noticeEntity.getNoticeTitle())
                .noticeContent(noticeEntity.getNoticeContent())
                .noticeViews(noticeEntity.getNoticeViews())
                .admin(noticeEntity.getAdmin())
                .createDate(noticeEntity.getCreateDate())
                .modifyDate(noticeEntity.getModifyDate())
                .build();
        return noticeDto;
    }

    // 공지사항 조회수 +1
    @Transactional
    public int addViewCount(Long noticeNum, int noticeViews) {
        return noticeRepository.addViewCount(noticeNum,noticeViews);
    }

}
