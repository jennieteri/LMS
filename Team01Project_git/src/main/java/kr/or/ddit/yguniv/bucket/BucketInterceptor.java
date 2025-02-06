package kr.or.ddit.yguniv.bucket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class BucketInterceptor implements HandlerInterceptor {
	private final Bucket bucket;

	// 인터셉터를 구현 요청이 들어오고 먼저 인터셉트 -> 버킷 실행 - > 유효 true 그대로 진행
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	        throws Exception {
	    if (bucket.tryConsume(1)) {
	        log.info(" ＃＃＃＃＃캐치 성공 토큰 유효＃＃＃＃dd＃＃＃＃＃＃＃＃＃＃＃＃＃＃＃dd＃＃{}", "success!");
	        return true;
	    } else {
	        log.info(" ＃＃＃＃＃캐치 성공 토큰 부족＃＃＃＃＃＃＃dd＃＃＃＃＃＃＃＃＃＃＃dd＃＃＃＃{}", "NONONONONO");
	        response.sendError(HttpStatus.TOO_MANY_REQUESTS.value());
	        return false;
	    }
	}
	
	
	
	
	
}
