package kr.or.ddit.yguniv.batch.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Formatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.batch.dao.BatchMapper;
import kr.or.ddit.yguniv.vo.AttendeeVO;
import kr.or.ddit.yguniv.vo.LectureCartVO;
import kr.or.ddit.yguniv.vo.PersonVO;
import kr.or.ddit.yguniv.vo.StudentVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BatchServiceImpl {

	@Autowired
	private final BatchMapper dao;

	// 수강신청 테이블 전체
	public List<LectureCartVO> selectCartList() {
		return dao.selectCartList();
	}

	// attendee 테이블에 추가
	public int insertAttendee(AttendeeVO aVo) {
		return dao.insertAttendee(aVo);
	}

	// 성공하면 카트 다 삭제
	public int deleteCart() {
		return dao.deleteCart();
	}

	// 학생 계정 가져오기
	public List<StudentVO> selectStudentList() {
		return dao.selectStudentList();
	}

	// 현재 일자와 학생 최근접속일자 비교로직 생성하기. formmater와 checker
	// 날짜 포맷
	public StudentVO dateFormatter(StudentVO sVo) {

		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate lastConnectDate = LocalDate.parse(sVo.getLastConectDe(), formatter);

		long monthsBetween = ChronoUnit.MONTHS.between(lastConnectDate, currentDate);

		log.info("monthsBetween: {}", monthsBetween);
		if (monthsBetween >= 3) {
			sVo.setDormantDate(currentDate.format(formatter));
			return sVo;
		}

		return null;
	}

	// 휴면일자 업데이트
	public int updateStudentDormant(StudentVO sVo) {
		return dao.updateStudentDormant(sVo);
	}

}
