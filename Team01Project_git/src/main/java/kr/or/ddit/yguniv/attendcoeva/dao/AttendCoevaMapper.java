package kr.or.ddit.yguniv.attendcoeva.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.vo.AttendeeVO;
import kr.or.ddit.yguniv.vo.DummyVO;
import kr.or.ddit.yguniv.vo.LectureEvaluationStandardVO;
import kr.or.ddit.yguniv.vo.SemesterVO;

@Mapper
public interface AttendCoevaMapper {
	
	//강의평가 LCRUD
	//목록 : 등록 가능한 강의 평가 목록 가져오기[학생] / 등록된 강의 평가 가져오기 [교수]
	//상세 : 해당 강의의 강의평가 가져오기 [ 교수? 학생]?
	//생성 : 학생이 새로운 강의평가를 등록?
	//수정 : 학생이 새로운 강의평가를 수정?.

	/** 
	 * 강의 평가 가져오기
	 * @return [학생] 등록 가능한 강의평가 목록
	 */
	public List<SemesterVO> selectAttenSemesterList(String stuId); //강의 LectureVO로 수정하기
	/** 
	 * 강의 평가 가져오기
	 * @return  [교수] 등록된 강의 평가 가져오기
	 */
	public List<SemesterVO> selectProfeSemesterList(String profeId); //강의 LectureVO로 수정하기
	
	public List<AttendeeVO> selectCoevaList(@Param("stuId") String stuId, @Param("semstrNo") String semstrNo); //강의 LectureVO로 수정하기
	public AttendeeVO selectCoevaDetail(@Param("stuId") String stuId,@Param("lectNo") String lectNo, @Param("semstrNo") String semstrNo); //강의 LectureVO로 수정하기
	public int updateAttenScore(@Param("aVo") AttendeeVO aVo); //강의 LectureVO로 수정하기
	
	//myPage 용도
	public List<AttendeeVO> selectMapageList(@Param("stuId") String stuId); 
	
	/**
	 * [교수]한 건의 강의 평가 조회하기
	 * @return DummyVO / NULL
	 */
	public List<AttendeeVO> selectProfeCoevaList (@Param("profeId") String profeId, @Param("semstrNo") String semstrNo);

	
	public int insertCoeva(AttendeeVO aVo);


	public int updateCoeva(@Param("aVo") AttendeeVO aVo);
	
	public AttendeeVO selectCoevaOne(@Param("stuId") String stuId,@Param("lectNo") String lectNo);
	
	public List<LectureEvaluationStandardVO> selectLectureEvaluationStandardOne(@Param("lectNo") String lectNo); 
	
	public List<AttendeeVO> selectProfeCoevaDetail (@Param("profeId")String profeId,@Param("lectNo") String lectNo, @Param("semstrNo") String semstrNo);
	
	
	public int deleteCoeva();
}














