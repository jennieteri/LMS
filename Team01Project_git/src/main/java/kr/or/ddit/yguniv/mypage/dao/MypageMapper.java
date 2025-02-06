package kr.or.ddit.yguniv.mypage.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.vo.PersonVO;

@Mapper
public interface MypageMapper {
	// 마피페이지 조회 
	public PersonVO selectMyPage(String id);
	
	// 마이페이지 수정
	public int updateMyPage(PersonVO person);
	
	
	// 기존 비밀번호 조회
    String getPass(String id);

    // 비밀번호 업데이트
    void updatePass(@Param("id") String id, @Param("newPassword") String newPassword);

	public int updateStudentCategoryWithMerge(PersonVO person);
	

}
