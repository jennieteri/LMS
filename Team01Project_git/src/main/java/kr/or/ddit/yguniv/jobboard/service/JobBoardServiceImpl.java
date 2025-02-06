package kr.or.ddit.yguniv.jobboard.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import kr.or.ddit.yguniv.atch.service.AtchFileService;
import kr.or.ddit.yguniv.board.answerBoard.exception.BoardException;
import kr.or.ddit.yguniv.commons.enumpkg.ServiceResult;
import kr.or.ddit.yguniv.jobboard.dao.JobBoardMapper;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.AtchFileVO;
import kr.or.ddit.yguniv.vo.JobBoardVO;
import kr.or.ddit.yguniv.vo.JobTestResultVO;
import kr.or.ddit.yguniv.vo.PersonVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobBoardServiceImpl implements JobBoardService {
	
	@Autowired
	private final JobBoardMapper mapper;
	@Autowired
	private final AtchFileService atchFileService;

	@Value("#{dirInfo.fsaveDir}")
	private Resource saveFolderRes;
	private File saveFolder;

	@PostConstruct
	public void init() throws IOException {
		this.saveFolder = saveFolderRes.getFile();
	}

	@Override
	public List<JobBoardVO> selectJobBoardListPaging(PaginationInfo<JobBoardVO> paging) {
		paging.setTotalRecord(mapper.selectTotalRecord(paging));
		List<JobBoardVO> boardList = mapper.selectJobBoardListPaging(paging);
		return boardList;
	}

	@Override
	public JobBoardVO selectJobBoard(String jobNo) {
		JobBoardVO board = mapper.selectJobBoard(jobNo);

		mapper.jobCount(jobNo);
		return board;
	}

	@Override
	public AtchFileDetailVO download(int atchFileId, int fileSn) {
		return Optional.ofNullable(atchFileService.readAtchFileDetail(atchFileId, fileSn, saveFolder))
				.filter(fd -> fd.getSavedFile().exists())
				.orElseThrow(() -> new BoardException(String.format("[%d, %d]해당 파일이 없음.", atchFileId, fileSn)));
	}

	@Override
	public void insertJobBoard(final JobBoardVO board) {

		Integer atchFileId = Optional.ofNullable(board.getAtchFile())
				.filter(af -> !CollectionUtils.isEmpty(af.getFileDetails())).map(af -> {
					atchFileService.createAtchFile(af, saveFolder);
					return af.getAtchFileId();
				}).orElse(null);

		board.setAtchFileId(atchFileId);
		mapper.insertJobBoard(board);

	}

	// 조회수 증가
	@Override
	public int jobCount(String jobNo) {
		return 0;
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
				Optional.ofNullable(savedAtchFile).filter(saf -> !CollectionUtils.isEmpty(saf.getFileDetails()))
						.map(saf -> saf.getFileDetails().stream()).orElse(Stream.empty()),
				Optional.ofNullable(newAtchFile).filter(naf -> !CollectionUtils.isEmpty(naf.getFileDetails()))
						.map(naf -> naf.getFileDetails().stream()).orElse(Stream.empty()))
				.collect(Collectors.toList());

		// 병합된 파일 리스트 설정
		mergeAtchFile.setFileDetails(fileDetails);

		// 병합된 파일이 존재할 경우 저장
		if (!mergeAtchFile.getFileDetails().isEmpty()) {
			atchFileService.createAtchFile(mergeAtchFile, saveFolder);
		}

		if (savedAtchFile != null && savedAtchFile.getFileDetails() != null) {
			// 기존 첨부파일 그룹은 비활성화
			atchFileService.disableAtchFile(savedAtchFile.getAtchFileId());
		}

		return mergeAtchFile.getAtchFileId();
	}

	// 게시글 수정
	@Override
	public void updateJobBoard(final JobBoardVO board) {
		final JobBoardVO saved = selectJobBoard(board.getJobNo());

		Integer newAtchFileId = Optional.ofNullable(board.getAtchFile()).filter(af -> af.getFileDetails() != null)
				.map(af -> mergeSavedDetailsAndNewDetails(saved.getAtchFile(), af)).orElse(null);

		board.setAtchFileId(newAtchFileId);

		mapper.updateJobBoard(board);
	}

	@Override
	public ServiceResult deletejobBoard(JobBoardVO board) {
		JobBoardVO saved = selectJobBoard(board.getJobNo());

		// 게시글 없는경우
		if (saved == null) {
			return ServiceResult.FAIL;
		}

		int delete = mapper.deletejobBoard(board.getJobNo());
		if (delete > 0) {
			return ServiceResult.OK;
		} else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public List<Map<String, Object>> jobBoardStatics() {
		return mapper.jobBoardStatistics();
	}

	@Override
	public int isUserRegisteredForJobBoard(String userId, String jobNo) {
		return mapper.isUserRegisteredForJobBoard(userId, jobNo);
	}

}
