package com.project.betterNow.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "비회원"),
    MEMBER("ROLE_MEMBER", "회원"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;

}
