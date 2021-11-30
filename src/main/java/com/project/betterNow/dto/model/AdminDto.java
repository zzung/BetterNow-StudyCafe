package com.project.betterNow.dto.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
public class AdminDto implements UserDetails {

    @NotNull
    @ApiModelProperty(required = true, value = "관리자 번호")
    private Long adminNum;

    @NotNull
    @ApiModelProperty(value = "관리자 아이디")
    private String adminId;

    @NotNull
    @ApiModelProperty(value = "관리자 비밀번호")
    private String adminPwd;

    @ApiModelProperty(value = "관리자 권한 정보")
    private String auth;

    @Builder
    public static AdminDto admin(String adminId, String adminPwd) {
        AdminDto adminDto = new AdminDto();
        adminDto.adminId = adminId;
        adminDto.adminPwd = adminPwd;
        return adminDto;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for(String role : auth.split(",")){
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    @Override
    public String getPassword() {
        return adminPwd;
    }

    @Override
    public String getUsername() {
        return adminId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 만료 x
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
