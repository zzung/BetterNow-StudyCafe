package com.project.betterNow.config;

import com.project.betterNow.domain.entity.Member;
import com.project.betterNow.dto.model.OAuthAttributes;
import com.project.betterNow.dto.model.SessionUser;
import com.project.betterNow.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/**
 * google 로그인 이후 가져온 사용자의 정보등을 기반으로 가입 및 정보수정, 세션 저장 등의 기능 지원하는 클래스
 * */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // registrationId : 현재 로그인 진행중인 서비스를 구분하는 코드 (네이버/카카오/구글)
        // userNameAttributeName : oauth2 로그인 진행시 주어지는 일종의 primary Key (구글 기본코드 : sub)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuthAttributes : 여러가지 소셜 로그인 진행시 사용할 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        Member mem = saveOrUpdate(attributes);

        // SessionUser: 세션에 사용자 정보를 저장하기 위한 클래스
        httpSession.setAttribute("member", new SessionUser(mem));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(mem.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    // 내 프로필 사진 변경
    private Member saveOrUpdate(OAuthAttributes attributes) {
        Member mem = memberRepository.findByMemId(attributes.getEmail())
                .map(entity -> entity.updateMyPicture(attributes.getEmail(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return memberRepository.save(mem);
    }


}
