package kr.or.ddit.yguniv.login.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import kr.or.ddit.yguniv.login.dao.PersonDAO;
import kr.or.ddit.yguniv.vo.PersonVO;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Component
public class CustomAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	private final PersonDAO person; // DAO 의존성 주입

    public void onAuthenticationSuccess(
    		HttpServletRequest request, 
    		HttpServletResponse response, 
    		Authentication authentication
    		) throws IOException, ServletException {
        
    	String id = authentication.getName(); // 인증된 사용자 ID 가져오기
       
        try {
        	PersonVO user = person.selectPersonForAuth(id); //1차 로그인한 사용자 정보가져오기 2차인증 수단확인

        	if (user == null) {
                 throw new UsernameNotFoundException("사용자 정보를 찾을 수 없습니다.");
             }
        	 person.resetFail(id);  // 로그인 성공 시 실패 횟수 초기화
        	
        	 if(user.getStreCateCd() != null && "SC06".equals(user.getStreCateCd())) { // 신입생 판단 여부 
        		 getRedirectStrategy().sendRedirect(request, response, "/");
        		 return;
        	 }
        	 
        } catch (Exception e) {
            e.printStackTrace(); // 예외 발생 시 로그 출력
        }
        
        HttpSession session = request.getSession();   // 2차 인증 데이터를 세션에 저장
       
        session.setAttribute("is2FAAuthenticated", false);  // 2차 인증 미완료 상태
        
        getRedirectStrategy().sendRedirect(request, response, "/2fa"); // 2차 인증 페이지로 리다이렉트
    }
}


