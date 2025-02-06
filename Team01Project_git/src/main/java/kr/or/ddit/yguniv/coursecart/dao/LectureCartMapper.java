package kr.or.ddit.yguniv.coursecart.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.DummyVO;
import kr.or.ddit.yguniv.vo.LectureCartVO;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.NoticeBoardVO;

@Mapper
public interface LectureCartMapper {
	//수강신청 에서의 LCRUD
	//목록 : 강의 목록 가져오기
	//상세 : 해당 강의의 강의 계획서 가져오기
	//생성 : ?? 학생은 계정당 하나의 장바구니를 이미 소유한다.-> 학생 계정이 생성될 때 장바구니가 하나 만들어져야함.
	// 		 이 만들어진 장바구니에 강의를 담고 삭제해야함. 그럼 생성인가 삭제인다
	//수정 : ?? 
	//삭제 : 수강신청 장바구니에서 삭제
	
	public List<LectureVO> getLectureList();

	public List<LectureVO> getStudentLectureList(String stuId);
	public List<LectureVO> attendeeStudentLectureList(String stuId);
	public LectureVO selectLecturePaper (String lectNo);

	
	public int directInsertOneCart(LectureCartVO lcVo);

	public int insertCart(LectureCartVO lcVo);
	
	public int updateCart(LectureCartVO lcVo);
	
	public boolean selectCart(LectureCartVO lcVo);
	
	public int deleteCart(LectureCartVO lcVo);

	public int getLectureCartAttendeeCount(String lectNo);
	public int getLectureAttendeeCount(String lectNo);
	
	public NoticeBoardVO getPreLectureCartDate();
	
	public NoticeBoardVO getPostLectureCartDate();
	
	
}



