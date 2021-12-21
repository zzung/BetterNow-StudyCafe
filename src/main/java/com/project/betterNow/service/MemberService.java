package com.project.betterNow.service;

import com.project.betterNow.domain.entity.Admin;
import com.project.betterNow.domain.entity.Member;
import com.project.betterNow.domain.entity.Role;
import com.project.betterNow.domain.repository.AdminRepository;
import com.project.betterNow.domain.repository.MemberRepository;
import com.project.betterNow.dto.model.MemberDto;
import com.project.betterNow.dto.request.SignupRq;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private MemberRepository memberRepository;
    private AdminRepository adminRepository;

    // 회원 가입
    @Transactional
    public Long signup(MemberDto memberDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        memberDto.setMemPwd(encoder.encode(memberDto.getMemPwd()));
        return memberRepository.save(Member.builder()
                .memId(memberDto.getMemId())
                .memPwd(memberDto.getMemPwd())
                .phone(memberDto.getPhone())
                .memYn('Y')
                .auth("ROLE_MEMBER").build()).getMemNum();
    }

    /** 로그인을 위해 가입된 member 상세 정보 조회 메서드
     * return 타입 UserDetails ==> 사용자의 계정정보 + 권한 정보
     * 매개 변수 username       ==> 아이디를 의미, 로그인 form 에서 name="username" 으로 요청 해야 함
     * return 값 new User()   ==> SpringSecurity 에서 제공하는 UserDetail을 구현한 User 사용(아이디,비밀번호, 권한리스트)
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> memberWrapper = memberRepository.findByMemId(username);
        Optional<Admin> adminWrapper = adminRepository.findByAdminId(username);
        List<GrantedAuthority> authorities = new ArrayList<>();

        boolean memberExist = memberWrapper.isPresent();
        boolean adminExist = adminWrapper.isPresent();

        if(memberExist) { // 해당 아이디가 member에 있을 경우
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getKey()));
            return new User(memberWrapper.get().getMemId(), memberWrapper.get().getMemPwd(), authorities);
        }

        if(adminExist) { // 해당 아이디가 admin에 있을 경우
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getKey()));
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getKey()));
            return new User(adminWrapper.get().getAdminId(),adminWrapper.get().getAdminPwd(), authorities);
        }

        throw new UsernameNotFoundException(username);
    }

    // 아이디 찾기
    public Member findByPhone(String phone) {
        Optional<Member> memberWrapper =  memberRepository.findByPhone(phone);
        if(memberWrapper.isPresent()) {
            Member member = memberWrapper.get();
            return member;
        }
        return null;
    }

    // 비밀번호 찾기
    public Member findPwdByMemIdAndPhone(String memId, String phone) {
        Optional<Member> memberWrapper = memberRepository.findByMemIdAndPhone(memId, phone);
        return memberWrapper.isPresent() ? memberWrapper.get() : null;
    }

    // 비밀번호 변경
    @Transactional
    public int updateMemPwd(String memPwd, String memId) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encoderPwd = encoder.encode(memPwd);
        return memberRepository.updateMemPwd(encoderPwd, memId);
    }

    // memId로 Member 찾기
    public Member findByMemId(String memId) {
        Optional<Member> memberWrapper =  memberRepository.findByMemId(memId);
        if(memberWrapper.isPresent()) {
            Member member = memberWrapper.get();
            return member;
        }
        return null;
    }
}
