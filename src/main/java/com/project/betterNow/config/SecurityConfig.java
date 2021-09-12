package com.project.betterNow.config;

import com.project.betterNow.domain.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()     //h2-console 화면 사용하기 위한 disable
                .and()
                    .authorizeRequests()                //URL별 관리 설정 옵션의 시작점 (antMatchers 옵션을 사용하기 위해 필수 선언)
                    .antMatchers("/user/**").authenticated() // 권한을 가진 사람만 접근 가능
                    .antMatchers("/admin/**").hasRole(Role.ADMIN.name()) // 관리자만 접근 가능
                    .anyRequest().permitAll()
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/")
                .and()
                    .logout()
                        .logoutSuccessUrl("/") // logout 성공시 '/'주소로 이동
                .and()
                    .oauth2Login() // oauth2 로그인 기능 설정 옵션의 시작점
                        .userInfoEndpoint() // oauth2 로그인 성공 후 사용자 정보 가져오는 설정 담당
                            .userService(customOAuth2UserService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring().antMatchers("/css/**", "/img/**", "/js/**", "/summernote/**");
    }
}
