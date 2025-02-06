package kr.or.ddit.yguniv.lecture.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.LectureWeekVO;
import kr.or.ddit.yguniv.vo.OrderLectureDataVO;

@Mapper
public interface LectureMaterialsMapper {
	/**
	 * R-강의자료 목록 조회
	 */
	public List<LectureWeekVO> selectOrderLectureDataList(@Param("lectNo") String lectNo, @Param("id") String id);
	/**
	 * R-강의자료 단건 조회
	 */
	public OrderLectureDataVO selectOrderLectureData(OrderLectureDataVO orderLectureDataVO);
	/**
	 * R-강의 주차 추가 전 PK 중복인지 확인
	 */
	public int selectLectureWeek(LectureWeekVO lectureWeekVO);
	/**
	 * C-강의 주차 추가
	 */
	public int insertLectureWeek(LectureWeekVO lectureWeekVO);
	/**
	 * U-강의 주차 수정
	 */
	public int updateLectureWeek(LectureWeekVO lectureWeekVO);
	/**
	 * D-강의 주차 삭제
	 */
	public int deleteLectureWeek(LectureWeekVO lectureWeekVO);
	/**
	 * C-강의 차시 추가
	 */
	public int insertOrderLectureData(OrderLectureDataVO orderLectureDataVO);
	/**
	 * U-강의 차시 수정
	 */
	public int updateOrderLectureData(OrderLectureDataVO orderLectureDataVO);
	/**
	 * D-강의 차시 삭제
	 */
	public int deleteOrderLectureData(OrderLectureDataVO orderLectureDataVO);
	/**
	 * R-강의 존재 확인
	 */
	public LectureVO selectLecture(@Param("lectNo") String lectNo);
}
