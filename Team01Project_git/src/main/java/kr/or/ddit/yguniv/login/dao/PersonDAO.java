package kr.or.ddit.yguniv.login.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import kr.or.ddit.yguniv.security.conf.PersonVoWrapper;
import kr.or.ddit.yguniv.vo.PersonVO;

@Mapper
public interface PersonDAO extends UserDetailsService {

	@Override
	default UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		PersonVO realUser = selectPersonForAuth(username);

		if (realUser == null) {
			throw new UsernameNotFoundException(String.format("%s 사용자없음.", username));
		}
		return new PersonVoWrapper(realUser);
	}

	/**
	 * 인증용 정보 조회 (아이디, 비밓번호, 이름, 이메일, 휴대폰 )
	 * 
	 * @param Id
	 * @return 존재하지 않으면 , null 반환
	 */
	public PersonVO selectPersonForAuth(@Param("id") String Id);

	/**
	 * 비밀번호 실패 횟수 증가
	 * 
	 * @param Id
	 * @return
	 */
	public int loginCount(@Param("id") String Id);

	/**
	 * 로그인 실패 횟수 조회
	 * 
	 * @param Id
	 * @return
	 */
	public int failLoginCount(@Param("id") String Id);

	/**
	 * 로그인 초기화
	 * 
	 * @param Id
	 */
	public int resetFail(@Param("id") String Id);
	
	public int mergeIntoPersonVisit(@Param("date")String date);

	
	

	

}














