package com.project.betterNow.dto.model;

import com.project.betterNow.domain.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Setter
@Getter
public class MemberDto implements UserDetails, Serializable {

    @ApiModelProperty(required = true, value = "회원 번호")
    private Long memNum;

    @ApiModelProperty(required = true, value = "회원 아이디")
    @NotBlank(message = "아이디는 필수 입력 값입니다. ")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String memId;

    @ApiModelProperty(required = true, value = "회원 비밀번호")
    @NotBlank(message = "비밀 번호는 필수 입력 값입니다.")
    @Pattern(regexp = "[a-zA-Z1-9]{6,12}", message = "영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요.")
    private String memPwd;

    @ApiModelProperty(required = true, value = "회원 연락처")
    @NotBlank(message = "휴대폰 번호는 필수 입력 값입니다. ")
    @Pattern(regexp = "^(010)[0-9]{4}[0-9]{4}", message = "숫자로만 11자리 모두 입력해주세요.")
    private String phone;

    @ApiModelProperty(value = "계정 상태")
    private Character memYn;

    @ApiModelProperty(value = "사용자 권한 정보")
    private String auth;

    /** JPA로 데이터베이스로 저장할 때 Enum 값을 어떤 형태로 저장할지 결정함
     * 기본적으로는 int타입으로 저장되지만 분별력이 떨어지기 때문에 String으로 저장되도록 설정
     *
     @Enumerated(EnumType.STRING)
     @Column(nullable = false)
     private Role role;
     */

    // 검색 필터
    private String keyword;


    public Member toEntity() {
        return Member.builder()
                .memId(memId)
                .memPwd(memPwd)
                .phone(phone)
                .auth(auth)
                .build();
    }

    @Builder
    public static MemberDto member(Long memNum, String memId, String memPwd, String phone) {
       MemberDto mem = new MemberDto();
       mem.memNum = memNum;
       mem.memId = memId;
       mem.memPwd = memPwd;
       mem.phone = phone;
       mem.memYn = 'Y';
       mem.auth = "AUTH_MEMBER";
       return mem;
    }

    // 사용자의 권한을 컬렉션 형태로 반환
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
        return memPwd;
    }

    // 사용자의 id 반환
    @Override
    public String getUsername() {
        return memId;
    }

    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true; // 만료 x
    }

    // 계정 잠금 여부
    @Override
    public boolean isAccountNonLocked() {
        return true; // 잠금 x
    }

    // 비밀번호 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 만료 x
    }

    // 계정 사용 가능 여부
    @Override
    public boolean isEnabled() {
        return true; //가능
    }


}
