package kr.or.ddit.yguniv.dissent.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.yguniv.attendcoeva.service.AttendCoevaService;
import kr.or.ddit.yguniv.dissent.dao.DissentMapper;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AttendeeVO;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.ScoreFormalObjectionVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DissentServiceImpl implements DissentService {

	private final DissentMapper dao;

	
	
	@Override
	public List<ScoreFormalObjectionVO> readDissentList() {
		//log.info("{}", dao.selectDissentList());

		return dao.selectDissentList();
	}
	@Override
	public List<ScoreFormalObjectionVO> readProfeDissentList(String profeId,PaginationInfo<ScoreFormalObjectionVO> paging,String lectNo) {
		//log.info("{}", dao.selectProfeDissentList(profeId,paging,lectNo));
		
		return dao.selectProfeDissentList(profeId,paging,lectNo);
	}

	@Override
	public ScoreFormalObjectionVO readDissentOne(String stuId,String lectNo) {

		return dao.selectDissentOne(stuId,lectNo);
	}

	@Override
	public Integer createDissent(ScoreFormalObjectionVO sVo) {
		
		return dao.insertDissent(sVo);
	}
	
	@Override
	public Integer modifyDissentAndAttendee(ScoreFormalObjectionVO sVo, AttendeeVO aVo) {

		Integer dis = dao.updateDissent(sVo);
		Integer ats = dao.updateAttendee(aVo);
//		log.info("22222222222222222222222222222222222222{}",aVo);

		
		if((dis==0||dis==null)||(ats==0||ats==null)) {
			throw new RuntimeException();
		}
		
		return dis+ats;
	}
	
	@Override
	public Integer removeDissent(String lectNo, String stuId) {

		return dao.deleteDissent(lectNo, stuId);
	}
	@Override
	public List<ScoreFormalObjectionVO> readAttenLectList(String stuId) {
	
		return dao.selectAttenLectList(stuId);
	}
	@Override
	public List<LectureVO> readProfeName(String lectNo) {
		return dao.selectProfeName(lectNo);
	}
	@Override
	public ScoreFormalObjectionVO readAttenLectOne(String stuId, String lectNo) {
		
		return dao.selectAttenLectOne(stuId, lectNo);
	}
	@Override
	public ScoreFormalObjectionVO selectAttendeeDissentOne(String stuId, String lectNo) {
		// TODO Auto-generated method stub
		return dao.selectAttendeeDissentOne(stuId, lectNo);
	}
	@Override
	public String selectOneProfeName(String lectNo) {
		// TODO Auto-generated method stub
		return dao.selectOneProfeName(lectNo);
	}
	

}
