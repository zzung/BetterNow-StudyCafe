package com.project.betterNow.dto.model;

import com.project.betterNow.domain.entity.Member;
import com.project.betterNow.domain.entity.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String,Object> attributes, String nameAttributeKey,String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.email = email;
        this.picture = picture;
    }

    /** of()
     * OAuth2User 에서 반환하는 사용자 정보가 'Map 형태' 이므로 값 매칭 시켜주기
     * */
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    /** toEntity()
     * OAuthAttributes 에서 Member 엔티티 생성 : 처음 가입할 때
     * Role.GUEST : 가입할 때 기본 권한 부여
     * */
    public Member toEntity() {
        return Member.builder()
                .memId(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
