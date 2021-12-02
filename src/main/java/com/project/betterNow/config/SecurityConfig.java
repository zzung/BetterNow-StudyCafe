package com.project.betterNow.config;

import com.project.betterNow.domain.entity.Role;
import com.project.betterNow.handler.MyLoginFailHandler;
import com.project.betterNow.handler.MyLoginSuccessHandler;
import com.project.betterNow.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberService memService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()     //h2-console 화면 사용하기 위한 disable
                .and()
                    .authorizeRequests()    //URL별 관리 설정 옵션의 시작점 (antMatchers 옵션을 사용하기 위해 필수 선언)
                    .antMatchers("/member/**").hasRole(Role.MEMBER.name())
                    .antMatchers("/mypage/**").hasRole(Role.MEMBER.name())
                    .antMatchers("/admin/**").hasRole(Role.ADMIN.name()) // 관리자만 접근 가능
                    .antMatchers("/board/write").hasRole(Role.MEMBER.name())
                    .antMatchers("/board/edit/**").hasRole(Role.MEMBER.name())
                    .antMatchers("/notice/write").hasRole(Role.ADMIN.name())
                    .antMatchers("/notice/edit/**").hasRole(Role.ADMIN.name())
                    .anyRequest().permitAll()
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .loginProcessingUrl("/doLogin")
                        .usernameParameter("loginId")
                        .passwordParameter("loginPwd")
                        .successHandler(new MyLoginSuccessHandler())
                        .failureHandler(new MyLoginFailHandler())
                .and()
                    .logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/") // logout 성공시 '/'주소로 이동
                        .invalidateHttpSession(true)  // session 날리기
                .and()
                .exceptionHandling().accessDeniedPage("/denied"); // 403 예외처리 핸들링
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring().antMatchers("/css/**", "/img/**", "/js/**", "/static/summernote/**");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

}
