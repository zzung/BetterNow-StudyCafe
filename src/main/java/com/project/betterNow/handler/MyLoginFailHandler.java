package com.project.betterNow.handler;

import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service("myLoginFailHandler")
public class MyLoginFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        // TODO Auto-generated method stub
        if(exception instanceof BadCredentialsException) {
            request.setAttribute("LoginFailMessage", "아이디 또는 비밀번호가 일치하지 않습니다.");
        }
        else if(exception instanceof DisabledException) {
            request.setAttribute("LoginFailMessage", "현재 사용할 수 없는 계정입니다.");
        }
        else request.setAttribute("LoginFailMessage", "해당 계정을 찾을 수 없습니다.");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
        dispatcher.forward(request, response);
    }
}
