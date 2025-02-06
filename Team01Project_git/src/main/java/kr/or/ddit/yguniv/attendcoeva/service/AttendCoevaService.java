package kr.or.ddit.yguniv.attendcoeva.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.vo.AttendeeVO;
import kr.or.ddit.yguniv.vo.DummyVO;
import kr.or.ddit.yguniv.vo.SemesterVO;

public interface AttendCoevaService {
	
	/** 
	 * 강의 평가 가져오기
	 * @return [학생] 등록 가능한 강의평가 목록 / [교수] 등록된 강의 평가 가져오기
	 */
	public List<SemesterVO> selectAttenSemesterList(String stuId); 
	public List<SemesterVO> selectProfeSemesterList(String profeId); 
	
	
	public List<AttendeeVO> selectCoevaList(String stuId, String semstrNo); 
	public AttendeeVO selectCoevaDetail(String stuId, String lectNo, String semstrNo); 

	//myPage 용도
	public List<AttendeeVO> selectMapageList(String stuId); 
	
	public List<AttendeeVO> selectProfeCoevaList (String profeId, String semstrNo);
	public List<AttendeeVO> selectProfeCoevaDetail (String profeId,String lectNo, String semstrNo);
	/**
	 * [교수]한 건의 강의 평가 조회하기
	 * @return DummyVO / NULL
	 */
	public AttendeeVO selectCoevaPaper ();

	/** [학생] 강의 평가 등록하기
	 * @return 성공 : 1 / 실패 : 0
	 */
	public int insertCoeva(AttendeeVO aVo);
	/** [교수] 강의 평가 후 성적 업데이트
	 * @return 성공 : 1 / 실패 : 0
	 */
	public int updateCoeva(AttendeeVO aVo);
	
	
	public int deleteCoeva();
	public int updateAttendeeScore(AttendeeVO paVo);

}
