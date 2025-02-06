package kr.or.ddit.yguniv.dissent.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AttendeeVO;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.ScoreFormalObjectionVO;

public interface DissentService {

	
	public List<ScoreFormalObjectionVO> readDissentList ();
	public List<ScoreFormalObjectionVO> readProfeDissentList (String profeId,PaginationInfo<ScoreFormalObjectionVO> paging,String lectNo);
	public ScoreFormalObjectionVO readDissentOne(String stuId,String lectNo);
	public Integer createDissent(ScoreFormalObjectionVO sVo);
	public Integer modifyDissentAndAttendee(ScoreFormalObjectionVO sVo,AttendeeVO aVo);
	public Integer removeDissent(String lectNo,String stuId);
	public ScoreFormalObjectionVO selectAttendeeDissentOne(String stuId, String lectNo); 	
	
	
	public List<ScoreFormalObjectionVO> readAttenLectList(String stuId);
	public ScoreFormalObjectionVO readAttenLectOne(@Param("stuId")String stuId,@Param("lectNo")String lectNo);
	public List<LectureVO> readProfeName(String lectNo);
	public String selectOneProfeName(String lectNo);
}


