package kr.or.ddit.yguniv.projectTask.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import kr.or.ddit.yguniv.assignment.service.AssignmentService;
import kr.or.ddit.yguniv.atch.service.AtchFileService;
import kr.or.ddit.yguniv.noticeboard.exception.BoardException;
import kr.or.ddit.yguniv.projectTask.dao.ProjectTaskMapper;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.AtchFileVO;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.ProjectTaskVO;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ProjectTaskServiceImpl implements ProjectTaskService {
	private final ProjectTaskMapper mapper;
	private final AtchFileService atchFileService;
	private final AssignmentService assignmentService;
	
	@Value("#{dirInfo.fsaveDir}")
	private Resource saveFolderRes;
	private File saveFolder;
	
	@PostConstruct
	public void init() throws IOException {
		this.saveFolder = saveFolderRes.getFile();
	}
	

	@Override
	public void createProjectTask(ProjectTaskVO projectTask) {
		LectureVO lecture = new LectureVO();
		
		try {
			 lecture = assignmentService.checkLecture(projectTask.getLectNo());
		}catch(RuntimeException e){
			throw new BoardException("입력 강의번호에 해당하는 강의가 없습니다.",e);
		}
		
		Integer atchFileId = Optional.ofNullable(projectTask.getAtchFile())
				.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
				.map(af -> {
					atchFileService.createAtchFile(af, saveFolder);
					return af.getAtchFileId();
				}).orElse(null);
		
		projectTask.setLecture(lecture);
		projectTask.setAtchFileId(atchFileId);
		
		int res = mapper.insertProjectTask(projectTask);
		if(res<=0) {
			throw new BoardException("강의 생성실패 서버오류!(database)");
		}
		
	}

	@Override
	public ProjectTaskVO readProjectTask(String taskNo) {
		
		try {
			ProjectTaskVO projectTask = mapper.selectProjectTask(taskNo);
			return projectTask;
		}catch(RuntimeException e) {
			throw new BoardException("해당프로젝트번호의 프로젝트는 존재하지않습니다.",e);
		}
		
		
	}

	@Override
	public List<ProjectTaskVO> readProjectTaskList(String lectNo) {
		if(lectNo==null || lectNo.isEmpty()) {
			throw new BoardException("강의번호가 입력되지않았습니다.");
		}
		
		return mapper.selectProjectTasklist(lectNo) ;
	}

	@Override
	public void modifyProjectTask(final ProjectTaskVO projectTask) {
		
		final ProjectTaskVO saved = mapper.selectProjectTask(projectTask.getTaskNo());
		
		Integer newAtchFileId = Optional.ofNullable(projectTask.getAtchFile())
				.filter(af -> af.getFileDetails() != null)
				.map(af ->mergeSavedDetailsAndNewDetails(saved.getAtchFile() , af))
				.orElse(null);
		
		projectTask.setAtchFileId(newAtchFileId);
		
		mapper.updateProjectTask(projectTask);
		
	}

	@Override
	public void removeProjectTask(String taskNo) {
		ProjectTaskVO projectTask = mapper.selectProjectTask(taskNo);
		
		Optional.ofNullable(projectTask.getAtchFileId())
		.ifPresent(fid -> atchFileService.disableAtchFile(fid));
		
		mapper.deleteProjectTask(taskNo);
	}

	@Override
	public AtchFileDetailVO download(int atchFileId, int fileSn) {
		return Optional.ofNullable(atchFileService.readAtchFileDetail(atchFileId, fileSn, saveFolder))
				.filter(fd -> fd.getSavedFile().exists())
				.orElseThrow(() -> new BoardException(String.format("[%d, %d]해당 파일이 없음.", atchFileId, fileSn)));
	}

	@Override
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
	
	
	
	
}
