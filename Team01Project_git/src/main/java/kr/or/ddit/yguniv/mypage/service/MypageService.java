package kr.or.ddit.yguniv.mypage.service;

import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.commons.enumpkg.ServiceResult;
import kr.or.ddit.yguniv.vo.PersonVO;

@Service
public interface MypageService {

	//회원정보 조회 
	public PersonVO selectPerson (String id);
	
	//회원정보 수정 
	public ServiceResult updatePerson(PersonVO person);

	//비밀번호 인증ㄴ
	public PersonVO authenticateUser(String id, String pswd);
	
}
