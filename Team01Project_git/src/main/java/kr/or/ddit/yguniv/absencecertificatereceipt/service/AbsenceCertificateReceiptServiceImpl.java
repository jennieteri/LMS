package kr.or.ddit.yguniv.absencecertificatereceipt.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import kr.or.ddit.yguniv.absencecertificatereceipt.dao.AbsenceCertificateReceiptMapper;
import kr.or.ddit.yguniv.atch.service.AtchFileService;
import kr.or.ddit.yguniv.attendance.dao.AttendanceMapper;
import kr.or.ddit.yguniv.noticeboard.exception.BoardException;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AbsenceCertificateReceiptVO;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.AtchFileVO;

@Service
public class AbsenceCertificateReceiptServiceImpl {
	
	@Autowired
	private AbsenceCertificateReceiptMapper mapper;
	
	@Autowired
	private AttendanceMapper attendanceMapper;
	
	@Autowired
	private AtchFileService atchFileService;
	
	@Value("#{dirInfo.saveDir}")
	private Resource saveFolderRes;
	private File saveFolder;

	@PostConstruct
	public void init() throws IOException {
		this.saveFolder = saveFolderRes.getFile();
	}
	
	/**
	 * 단건조회
	 */
	public AbsenceCertificateReceiptVO selectAbsenceCertificateReceipt(String AbsenceCd) {
		return mapper.selectAbsenceCertificateReceipt(AbsenceCd);
	}
	
	public List<AbsenceCertificateReceiptVO> AbsenceListDistinct() {
		return mapper.AbsenceListDistinct();
	}
	
	public List<AbsenceCertificateReceiptVO> selectAbsenceCertificateReceiptList(
			AbsenceCertificateReceiptVO absenceVO
			, PaginationInfo<AbsenceCertificateReceiptVO> paging
	){
		paging.setTotalRecord(mapper.selectTotalRecord(absenceVO, paging));
		return mapper.selectAbsenceCertificateReceiptList(absenceVO, paging);
	}
	
	public boolean insertAbsenceCertificateReceipt(AbsenceCertificateReceiptVO absenceCertificateReceiptVO) {
		Integer atchFileId = Optional.ofNullable(absenceCertificateReceiptVO.getAtchFile())
				.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
				.map(af -> {
					atchFileService.createAtchFile(af, saveFolder);
					return af.getAtchFileId();
				}).orElse(null);
		
		absenceCertificateReceiptVO.setAtchFileId(atchFileId);
		
		return mapper.insertAbsenceCertificateReceipt(absenceCertificateReceiptVO) > 0;
	}
	
	
	// 공결인증서'만' 업데이트하는 서비스
	public boolean updateAbsenceCertificateReceipt(AbsenceCertificateReceiptVO absenceCertificateReceiptVO) {
	    return mapper.updateAbsenceCertificateReceipt(absenceCertificateReceiptVO) > 0;
	}
	
	@Transactional // 둘중 하나라도 안되면 에러처리
	// 공결인정서 상태와 출결 상태를 동시에 업데이트
	public boolean updateAbsenceAndAttendance(AbsenceCertificateReceiptVO absenceCertificateReceiptVO) {
		int result1 = attendanceMapper.updateAttendance(absenceCertificateReceiptVO.getAttendanceVO());
		int result2 = mapper.updateAbsenceAndAttendance(absenceCertificateReceiptVO);
		
		if(result1 * result2 != 1) {
			throw new RuntimeException(); // 커스텀 에러처리 해도된다.
			// 트라이캐치를 사용해야함
		}
		
//	    return  mapper.updateAbsenceAndAttendance(absenceCertificateReceiptVO) > 0;
	    return true;
		
	}
	
	
	public boolean deleteAbsenceCertificateReceipt(String absenceCd) {
		return mapper.deleteAbsenceCertificateReceipt(absenceCd) > 0;
	}
	
	
	public AtchFileDetailVO download(int atchFileId, int fileSn) {
		return Optional.ofNullable(atchFileService.readAtchFileDetail(atchFileId, fileSn, saveFolder))
				.filter(fd -> fd.getSavedFile().exists())
				.orElseThrow(() -> new BoardException(String.format("[%d, %d]해당 파일이 없음.", atchFileId, fileSn)));
	}

	public void removeFile(int atchFileId, int fileSn) {
		atchFileService.removeAtchFileDetail(atchFileId, fileSn, saveFolder);
	}
	
	
	/**
	 * 기존의 첨부파일 그룹이 있는 경우, 신규 파일과 기존 파일 그룹을 병합해 저장함.
	 * 
	 * @param atchFileId
	 */
	private Integer mergeSavedDetailsAndNewDetails(AtchFileVO savedAtchFile, AtchFileVO newAtchFile) {
		// 새로 병합할 파일 정보 설정
		AtchFileVO mergeAtchFile = new AtchFileVO();
		
		// 기존 파일 그룹과 신규 파일 그룹 병합
		List<AtchFileDetailVO> fileDetails = Stream.concat(
			Optional.ofNullable(savedAtchFile)
					.filter(saf->!CollectionUtils.isEmpty(saf.getFileDetails()))
					.map(saf->saf.getFileDetails().stream())
					.orElse(Stream.empty())
			, Optional.ofNullable(newAtchFile)
					.filter(naf->!CollectionUtils.isEmpty(naf.getFileDetails()))
					.map(naf->naf.getFileDetails().stream())
					.orElse(Stream.empty())
		).collect(Collectors.toList());		
		
		 // 병합된 파일 리스트 설정		
		mergeAtchFile.setFileDetails(fileDetails);
		
		 // 병합된 파일이 존재할 경우 저장
		if( ! mergeAtchFile.getFileDetails().isEmpty() ) {
			atchFileService.createAtchFile(mergeAtchFile, saveFolder);
		}
		
		if (savedAtchFile != null && savedAtchFile.getFileDetails() != null) {
			// 기존 첨부파일 그룹은 비활성화
			atchFileService.disableAtchFile(savedAtchFile.getAtchFileId());
		}

		return mergeAtchFile.getAtchFileId();
	}
	
	
	// 공결 인정서 수정 (학생)
	public boolean updateAbsence(final AbsenceCertificateReceiptVO absenceVO) {
		final AbsenceCertificateReceiptVO saved = selectAbsenceCertificateReceipt(absenceVO.getAbsenceCd());

		Integer newAtchFileId = Optional.ofNullable(absenceVO.getAtchFile())
				.filter(af -> af.getFileDetails() != null)
				.map(af ->mergeSavedDetailsAndNewDetails(saved.getAtchFile() , af))
				.orElse(null);
		
		absenceVO.setAtchFileId(newAtchFileId);
		return mapper.updateAbsence(absenceVO) > 0;
	}
	
	
}
