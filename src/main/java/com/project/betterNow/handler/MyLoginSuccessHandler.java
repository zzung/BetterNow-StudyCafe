package com.project.betterNow.handler;

import com.project.betterNow.domain.entity.Member;
import com.project.betterNow.domain.repository.MemberRepository;
import com.project.betterNow.dto.model.MemberDto;
import com.project.betterNow.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component("myLoginSuccessHandler")
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStratgy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        resultRedirectStrategy(request, response, authentication);
        clearAuthenticationAttributes(request);
    }


    protected void resultRedirectStrategy(HttpServletRequest request, HttpServletResponse response,
                                          Authentication authentication) throws IOException, ServletException {

        // savedRequest 객체를 통해 getRedirectUrl (로그인화면을 보기 전에 갔던 url)을 가져옴
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if(savedRequest!=null) {
            String targetUrl = savedRequest.getRedirectUrl();
            redirectStratgy.sendRedirect(request, response, targetUrl);
        } else {
            redirectStratgy.sendRedirect(request, response, "/");
        }

    }

    // 로그인 실패 에러 세션 지우기
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null)
            return;
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }



}

