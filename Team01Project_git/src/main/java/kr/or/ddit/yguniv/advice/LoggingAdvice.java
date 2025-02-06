package kr.or.ddit.yguniv.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import lombok.extern.slf4j.Slf4j;

//얘만 로그를 찍을 수 있으면됨
@Slf4j
public class LoggingAdvice {
	
	public void before(JoinPoint  point) {
		long start = System.currentTimeMillis();
		Object target = point.getTarget();
		String targetName = target.getClass().getSimpleName();
		
		String methodName = point.getSignature().getName();
		Object[] args = point.getArgs();
		log.info("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓BEFORE〓〓〓〓〓〓〓〓〓〓〓〓〓{}.{}({})〓〓",targetName,methodName,args);
				
	}
	public void after(JoinPoint  point,Object retValue) {
		long end = System.currentTimeMillis();
		log.info("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓AFTER〓〓〓〓〓〓:반환값 : {}〓〓〓〓〓〓〓〓〓",retValue);
		
	}
	
	
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long start = System.currentTimeMillis();
		Object target = point.getTarget();
		String targetName = target.getClass().getSimpleName();
		String methodName = point.getSignature().getName();
		Object[] args = point.getArgs();
		log.info("〓〓〓〓〓〓〓BEFORE〓〓〓〓〓〓〓〓〓〓〓〓〓{}.{}({})〓〓",targetName,methodName,args);
		log.info("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓targetName {}〓〓",targetName);
		log.info("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓methodName {}〓〓",methodName);
		log.info("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓args {}〓〓",args);
		
		//타겟호출
		Object retValue = point.proceed(args);
		
		long end = System.currentTimeMillis();
		log.info("〓〓〓〓〓〓〓〓AFTER〓〓〓〓〓〓:반환값 : {}〓〓소요시간 {}〓〓〓",retValue,(end-start));
		log.info("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓:반환값 : {}〓〓〓",retValue);
		log.info("〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓소요시간 {}〓〓〓",(end-start));
		
		return retValue;
	}
	
	
}




