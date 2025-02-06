package kr.or.ddit.yguniv.student.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import kr.or.ddit.yguniv.atch.service.AtchFileService;
import kr.or.ddit.yguniv.commons.exception.SemesterDuplicatedException;
import kr.or.ddit.yguniv.lecture.dao.LectureMaterialsMapper;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.student.dao.StudentRecordsMapper;
import kr.or.ddit.yguniv.vo.AcademicProbationVO;
import kr.or.ddit.yguniv.vo.StudentRecordsVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentRecordsServiceImpl {
	private final StudentRecordsMapper srMapper;
	private final AtchFileService atchFileService;
	
	@Value("#{dirInfo.saveDir}")
	private Resource saveFolderRes;
	private File saveFolder;

	@PostConstruct
	public void init() throws IOException {
		this.saveFolder = saveFolderRes.getFile();
	}
	
	/**
	 * R-학적 변동 신청 목록 조회
	 * @param paging 
	 */
	public List<StudentRecordsVO> selectStudentRecordsList(PaginationInfo<StudentRecordsVO> paging){
		paging.setTotalRecord(srMapper.selectTotalRecord(paging));
		return srMapper.selectStudentRecordsList(paging);
	}
	/**
	 * R-학적 변동 신청 상세 조회
	 */
	public StudentRecordsVO selectStudentRecords(StudentRecordsVO studentRecordsVO){
		return srMapper.selectStudentRecords(studentRecordsVO);
	}
	/**
	 * R-학적 변동 신청 전 이전 요청 존재 여부 확인
	 */
	public String selectPrevRequest(String stuId) {
		return srMapper.selectPrevRequest(stuId);
	}
	/**
	 * C-학적 변동 신청
	 */
	public int insertStudentRecords(StudentRecordsVO studentRecordsVO){
		if(srMapper.selectPrevRequestSemester(studentRecordsVO)>0) {
			throw new SemesterDuplicatedException();
		}
		Integer atchFileId = Optional.ofNullable(studentRecordsVO.getAtchFile())
				.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
				.map(af -> {
					atchFileService.createAtchFile(af, saveFolder);
					return af.getAtchFileId();
				}).orElse(null);
		studentRecordsVO.setAtchFileId(atchFileId);
		
		return srMapper.insertStudentRecords(studentRecordsVO);
	}
	/**
	 * U-학적 변동 서류 취소
	 */
	public boolean cancelStudentRecords(StudentRecordsVO studentRecordsVO) {
		return srMapper.cancelStudentRecords(studentRecordsVO)>0;
	}
	/**
	 * U-학적 변동 서류 승인
	 */
	public boolean consentStudentRecords(StudentRecordsVO studentRecordsVO){
		// 진행상태가 최종 승인의 경우 학생의 학적을 변경
		if("PS04".equals(studentRecordsVO.getStreStatusCd())) {
			StudentRecordsVO updateVO = srMapper.selectStudentRecords(studentRecordsVO);
			updateVO.setStreStatusCd(studentRecordsVO.getStreStatusCd());
			srMapper.updateStudentRecords(updateVO);
		}
		return srMapper.consentStudentRecords(studentRecordsVO)>0;
	}
	/**
	 * U-학적 변동 서류 반려
	 */
	public boolean returnStudentRecords(StudentRecordsVO studentRecordsVO){
		return srMapper.returnStudentRecords(studentRecordsVO)>0;
	}
}
