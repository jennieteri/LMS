package kr.or.ddit.yguniv.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

public class FindBeanChildContext {
	
	public static<T> T findBean(Class<T> beanType) {
		ServletRequestAttributes ras = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ras.getRequest();
		WebApplicationContext context = RequestContextUtils.findWebApplicationContext(request);
		
		return context.getBean(beanType);
	}
}
