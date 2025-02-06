package kr.or.ddit.yguniv.security.conf.resource.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.userdetails.User;

import kr.or.ddit.yguniv.security.conf.resource.vo.SecuredResourceVO;
import kr.or.ddit.yguniv.vo.PersonVO;

@Mapper
public interface SecuredResourceMapper {
	public List<SecuredResourceVO> selectResourceList();
	  // 사용자 정보 조회 메서드 추가
    public Optional<PersonVO> findByUsername(String username);
	
}
