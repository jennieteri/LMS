package kr.or.ddit.yguniv.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import kr.or.ddit.yguniv.log.service.LogService;
import kr.or.ddit.yguniv.vo.LogVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ControllerInterceptor implements HandlerInterceptor {
    
	private final LogService service;
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        
        if (handler instanceof HandlerMethod) {
        	 // Spring Security의 Authentication 객체에서 사용자 정보 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = (authentication != null && authentication.isAuthenticated()) ? authentication.getName() : "unknown";

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String method = request.getMethod();
            String controllerName = handlerMethod.getBeanType().getSimpleName();
            
            if(controllerName.equals("MyCalendarController")||controllerName.equals("NotificationController")) {
            	return true;            	
            }
            
            
            LogVO logVo = new LogVO();
            logVo.setId(userId);
            logVo.setLogContNm(controllerName);
            logVo.setLogMethod(method);
            
            service.mergeIntoLog(logVo);
            
        }
        
        return true;
    }
}

















