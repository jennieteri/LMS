package kr.or.ddit.yguniv.login.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public interface PersonFindLoginService {
	 

	public String selectFindForm(@Param("nm") String nm, @Param("brdt") String brdt);
	
	
}
