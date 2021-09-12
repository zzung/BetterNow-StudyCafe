package com.project.betterNow.dto.model;

import com.project.betterNow.domain.entity.Member;
import lombok.Getter;

@Getter
public class SessionUser {
    private String email;
    private String picture;

    public SessionUser(Member member) {
        this.email = member.getMemId();
        this.picture = member.getPicture();
    }
}
