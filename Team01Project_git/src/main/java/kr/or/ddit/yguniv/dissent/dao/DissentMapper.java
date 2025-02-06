package kr.or.ddit.yguniv.dissent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AttendeeVO;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.ScoreFormalObjectionVO;
import kr.or.ddit.yguniv.vo.SemesterVO;

@Mapper
public interface DissentMapper {
	

	//이의신청 LCRUD
	//목록 : 등록된 이의 신청 목록 가져오기 [교수]
	//상세 : 등록된 이의 신청 하나 조회 [ 교수? 학생]?
	//생성 : 학생이 새로운 이의 신청을 등록
	//수정 : 이의 신청 접수. 성적 변동 [교수]
	//삭제 : ??
	
   /** [교수] 등록된 이의 신청 목록 가져오기
     * @return  List<ScoreFormalObjectionVO> 
     */
    public List<ScoreFormalObjectionVO> selectDissentList(); 	

    public List<ScoreFormalObjectionVO> selectProfeDissentList(@Param("profeId")String profeId,@Param("paging") PaginationInfo<ScoreFormalObjectionVO> paging,@Param("lectNo") String lectNo); 	
   /** [교수] 등록된 이의 신청 하나 조회
     * @return ScoreFormalObjectionVO / NULL
     */
    public ScoreFormalObjectionVO selectDissentOne(@Param("stuId") String stuId, @Param("lectNo")String lectNo); 	
    public ScoreFormalObjectionVO selectAttendeeDissentOne(@Param("stuId") String stuId, @Param("lectNo")String lectNo); 	
   /** [학생] 하나의 이의 신청을 등록함
     * @return 성공1 / 실패0
     */
    public Integer insertDissent(ScoreFormalObjectionVO sVo); 	
   /** [교수] 이의 신청을 접수하여 학생의 성적을 수정함
     * @return 성공1 / 실패0
     */ 
    
    
   public Integer updateAttendee(AttendeeVO aVo); 	 
    /** [교수] 이의 신청을 접수하여 이의 신청 답변을 수정함
     * @return 성공1 / 실패0
     */ 
    public Integer updateDissent(ScoreFormalObjectionVO sVo); 	
 
    
    
    /** 이의 신청을 삭제?
     * @return 성공1 / 실패0
     */
    public Integer deleteDissent(String lectNo,String stuId); 	
    
    public List<ScoreFormalObjectionVO> selectAttenLectList(String stuId);
	
    /**
     * 기본 전송용 학기
     * @return
     */
    public List<SemesterVO> selectSemesterList();
    
    /**
     * 기본 전송용 강의리스트
     * @return
     */
    public List<LectureVO> selectLectureList();
    
    /**
     * 하나의 강의에 배정된 교수 조회용도
     * Param = 강의 번호
     * @return
     */
    public List<LectureVO> selectProfeName(String lectNo);
    public String selectOneProfeName(String lectNo);
   
    public ScoreFormalObjectionVO selectAttenLectOne(@Param("stuId")String stuId,@Param("lectNo")String lectNo);
    
}






















