package kr.or.ddit.yguniv.coursecart.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.yguniv.vo.LectureCartVO;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.NoticeBoardVO;

public interface LectureCartService {

	public List<LectureVO> getLectureList();
	public List<LectureVO> getStudentLectureList(String stuId);
	public List<LectureVO> attendeeStudentLectureList(String stuId);
	
	
	public LectureVO getLecturePaper(String lectNo);
	
	public int directInsertOneCart(LectureCartVO lcVo);
	
	public Map<String, List<String>> updateCart(List<LectureCartVO> lcVoList);
	public int insertCart(LectureCartVO lcVo);
	public int deleteCart(LectureCartVO lcVo);

	public NoticeBoardVO getPreLectureCartDate();
	
	public NoticeBoardVO getPostLectureCartDate();
	
	public boolean lectureCartDateChacker(NoticeBoardVO noticeBoardVO);
}

