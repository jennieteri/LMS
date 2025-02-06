package kr.or.ddit.yguniv.lecture.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.yguniv.annotation.RootContextWebConfig;
import kr.or.ddit.yguniv.vo.LectureWeekVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RootContextWebConfig
@Transactional
class LectureMaterialsServiceTest {
	@Autowired
	LectureMaterialsServiceImpl service;
	private LectureWeekVO lectureWeek;

	@Test
	void testDeleteLectureWeek() {
		lectureWeek = new LectureWeekVO();
		lectureWeek.setLectNo("L001");
		lectureWeek.setWeekCd("W02");
		service.deleteLectureWeek(lectureWeek);
	}

}
