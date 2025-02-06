package kr.or.ddit.yguniv.login.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import kr.or.ddit.yguniv.login.dao.PersonDAO;
import kr.or.ddit.yguniv.person.dao.PersonMapper;
import kr.or.ddit.yguniv.vo.PersonVO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Mapper
@Component
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final PersonDAO personDao;

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {

        String id = request.getParameter("id");
        String errorMessage = determineErrorMessage(exception, id);

        // 실패 메시지를 포함한 URL로 리다이렉트
        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");
        setDefaultFailureUrl("/loginForm?error=true&exception=" + errorMessage);

        // 부모 클래스의 기본 실패 처리 호출
        super.onAuthenticationFailure(request, response, exception);
    }

    /**
     * 예외에 따른 실패 메시지와 로그인 카운트 처리를 한 곳에서 처리
     */
    private String determineErrorMessage(AuthenticationException exception, String id) {
        if (id != null) {
            personDao.loginCount(id); // 중복 호출 방지: 한 번의 메서드에서 처리
        }

        if (exception instanceof BadCredentialsException) {
            return "아이디 또는 비밀번호가 일치하지 않습니다.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            return "내부 인증 시스템 오류입니다. 관리자에게 문의하세요.";
        } else if (exception instanceof UsernameNotFoundException) {
            return "존재하지 않는 계정입니다.";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            return "인증 요청이 거부되었습니다.";
        } else {
            return "알 수 없는 이유로 로그인에 실패했습니다.";
        }
    }
}


