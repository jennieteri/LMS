package kr.or.ddit.yguniv.lecture.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import kr.or.ddit.yguniv.atch.service.AtchFileService;
import kr.or.ddit.yguniv.commons.exception.PKDuplicatedException;
import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.lecture.dao.LectureMaterialsMapper;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.LectureWeekVO;
import kr.or.ddit.yguniv.vo.OrderLectureDataVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LectureMaterialsServiceImpl {
	private final LectureMaterialsMapper mapper;
	private final AtchFileService atchFileService;
	
	@Value("#{dirInfo.saveDir}")
	private Resource saveFolderRes;
	private File saveFolder;

	@PostConstruct
	public void init() throws IOException {
		this.saveFolder = saveFolderRes.getFile();
	}
	
	/**
	 * R-강의자료 목록 조회
	 */
	public List<LectureWeekVO> selectOrderLectureDataList(String lectNo, String id){
		return mapper.selectOrderLectureDataList(lectNo, id);
	}
	/**
	 * R-강의자료 상세 조회
	 */
	public OrderLectureDataVO selectOrderLectureData(OrderLectureDataVO orderLectureDataVO) {
		OrderLectureDataVO result = mapper.selectOrderLectureData(orderLectureDataVO);
		if(result == null) {
			throw new PKNotFoundException("해당 강의 차시는 존재하지 않습니다", true);
		}
		return result;
	}
	/**
	 * C-강의 주차 추가
	 */
	public void insertLectureWeek(LectureWeekVO lectureWeekVO) {
		if(mapper.selectLectureWeek(lectureWeekVO)>0) {
			throw new PKDuplicatedException("강의 주차가 중복됩니다");
		}
		mapper.insertLectureWeek(lectureWeekVO);
	}
	/**
	 * U-강의 주차 수정
	 */
	public void updateLectureWeek(LectureWeekVO lectureWeekVO) {
		if(mapper.selectLectureWeek(lectureWeekVO)==0) {
			throw new PKNotFoundException("해당 강의 주차가 존재하지 않습니다", true);
		}
		mapper.updateLectureWeek(lectureWeekVO);
	}
	/**
	 * D-강의 주차 삭제
	 */
	public void deleteLectureWeek(LectureWeekVO lectureWeekVO) {
		if(mapper.selectLectureWeek(lectureWeekVO)==0) {
			throw new PKNotFoundException("해당 강의 주차가 존재하지 않습니다", true);
		}
		// 주차 삭제 전 주차에 해당하는 차시 삭제
		OrderLectureDataVO orderLectureDataVO = new OrderLectureDataVO();
		orderLectureDataVO.setLectNo(lectureWeekVO.getLectNo());
		orderLectureDataVO.setWeekCd(lectureWeekVO.getWeekCd());
		deleteOrderLectureData(orderLectureDataVO);
		mapper.deleteLectureWeek(lectureWeekVO);
	}
	/**
	 * C-강의 차시 추가
	 */
	public void insertOrderLectureData(OrderLectureDataVO orderLectureDataVO) {
		if(mapper.selectOrderLectureData(orderLectureDataVO) != null) {
			throw new PKDuplicatedException("강의 차시가 중복됩니다");
		}
		
		Integer atchFileId = Optional.ofNullable(orderLectureDataVO.getAtchFile())
				.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
				.map(af -> {
					atchFileService.createAtchFile(af, saveFolder);
					return af.getAtchFileId();
				}).orElse(null);
		
		orderLectureDataVO.setAtchFileId(atchFileId);
		mapper.insertOrderLectureData(orderLectureDataVO);
	}
	/**
	 * U-강의 차시 수정
	 */
	public void updateOrderLectureData(OrderLectureDataVO orderLectureDataVO) {
		if(orderLectureDataVO.getLectOrder() != orderLectureDataVO.getOriginLectOrder()
				&& mapper.selectOrderLectureData(orderLectureDataVO) != null) {
			throw new PKDuplicatedException("강의 차시가 중복됩니다");
		}
		
		Integer atchFileId = Optional.ofNullable(orderLectureDataVO.getAtchFile())
				.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
				.map(af -> {
					atchFileService.createAtchFile(af, saveFolder);
					return af.getAtchFileId();
				}).orElse(null);
		
		orderLectureDataVO.setAtchFileId(atchFileId);
		mapper.updateOrderLectureData(orderLectureDataVO);
	}
	/**
	 * D-강의 차시 삭제
	 */
	public void deleteOrderLectureData(OrderLectureDataVO orderLectureDataVO) {
		mapper.deleteOrderLectureData(orderLectureDataVO);
	}
	/**
	 * R-강의 페이지 입장 전 존재하는 강의인지 확인
	 */
	public LectureVO selectLecture(String lectNo) {
		return mapper.selectLecture(lectNo);
	}
}
