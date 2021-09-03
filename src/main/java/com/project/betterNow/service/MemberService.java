package com.project.betterNow.service;

import com.project.betterNow.repository.member.MemberRepository;
import com.project.betterNow.dto.request.MemberJoinRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

//    @Transactional
//    public String save(MemberJoinRequestDto requestDto) {
//        return memberRepository.save(requestDto.toEntity());
//    }
}
