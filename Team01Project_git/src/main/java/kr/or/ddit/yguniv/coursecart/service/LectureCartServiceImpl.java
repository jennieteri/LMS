package kr.or.ddit.yguniv.coursecart.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.coursecart.dao.LectureCartMapper;
import kr.or.ddit.yguniv.vo.LectureCartVO;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.NoticeBoardVO;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class LectureCartServiceImpl implements LectureCartService {
	private final LectureCartMapper dao;
	
	@Override
	public NoticeBoardVO getPreLectureCartDate() {
		// TODO Auto-generated method stub
		return dao.getPreLectureCartDate();
	}

	@Override
	public NoticeBoardVO getPostLectureCartDate() {
		// TODO Auto-generated method stub
		return dao.getPostLectureCartDate();
	}
	
	@Override
	public boolean lectureCartDateChacker(NoticeBoardVO noticeBoardVO) {
		 boolean tf = false;

		    // 현재 날짜 구하기
		    LocalDate currentDate = LocalDate.now();

		    // 시작일과 종료일 가져오기
		    LocalDate startDate = noticeBoardVO.getNtcDt();
		    LocalDate endDate = noticeBoardVO.getNtcEt();

		    if ((currentDate.isEqual(startDate) || currentDate.isAfter(startDate)) &&
		        (currentDate.isEqual(endDate) || currentDate.isBefore(endDate))) {
		        tf = true;
		    }

		    return tf;
	}

	
	@Override
	public List<LectureVO> getLectureList() {
		return dao.getLectureList();
	}
	
	@Override
	public List<LectureVO> getStudentLectureList(String stuId) {
		return dao.getStudentLectureList(stuId);
	}

	@Override
	public LectureVO getLecturePaper(String lectNo) {
		return dao.selectLecturePaper(lectNo);
	}

	@Override
	public List<LectureVO> attendeeStudentLectureList(String stuId) {
		return dao.attendeeStudentLectureList(stuId);
	}
	
	@Override
	public int insertCart(LectureCartVO lcVo) {
		return dao.insertCart(lcVo);
	}
	
	@Override
	public int deleteCart(LectureCartVO lcVo) {
		return dao.deleteCart(lcVo);
	}
	
	// 다건 수강신청(wait => succ으로 바꾸는 update작업)
	@Override
	public Map<String, List<String>> updateCart(List<LectureCartVO> lcVoList) {
		Map<String, List<String>> resMap = new HashMap<>();
		List<String> succ = new ArrayList<>();
		List<String> fail = new ArrayList<>();
		resMap.put("succ", succ);
		resMap.put("fail", fail);
		int result = 0;
		for(LectureCartVO lcVo : lcVoList) {
			if(directInsertOneCart(lcVo)>0) {
				succ.add(lcVo.getLectNo());
			}else {
				fail.add(lcVo.getLectNo());
			}
		}
		return resMap;
	}
	
	// 단건 수강신청 가능여부 조회로직 포함
	@Override
	public int directInsertOneCart(LectureCartVO lcVo) {
		int result = 0;
		String lectNo = lcVo.getLectNo();
		int cartCount = dao.getLectureCartAttendeeCount(lectNo);
		int lectCount = dao.getLectureAttendeeCount(lectNo);
		if(cartCount<lectCount) {  //현재 강의 신청자 수 < 강의 모집 인원 수
			result = dao.updateCart(lcVo);
		}
		return result;
	}

	
}
