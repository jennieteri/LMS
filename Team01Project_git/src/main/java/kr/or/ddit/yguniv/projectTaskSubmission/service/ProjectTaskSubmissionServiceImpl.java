package kr.or.ddit.yguniv.projectTaskSubmission.service;

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

import kr.or.ddit.yguniv.atch.service.AtchFileService;
import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.noticeboard.exception.BoardException;
import kr.or.ddit.yguniv.projectMember.service.ProjectMemberService;
import kr.or.ddit.yguniv.projectTaskSubmission.dao.ProjectTaskSubmissionMapper;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.AtchFileVO;
import kr.or.ddit.yguniv.vo.ProjectTaskSubmissionVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectTaskSubmissionServiceImpl implements ProjectTaskSubmissionService {
	private final ProjectTaskSubmissionMapper mapper;
	private final ProjectMemberService projectMemberservice;
	private final AtchFileService atchFileService;
	
	@Value("#{dirInfo.fsaveDir}")
	private Resource saveFolderRes;
	private File saveFolder;
	
	@PostConstruct
	public void init() throws IOException {
		this.saveFolder = saveFolderRes.getFile();
	}
	
	@Override
	public int createProjectTaskSubmission(ProjectTaskSubmissionVO projectTaskSubmission) {
		
		if(projectTaskSubmission.getTaskNo()!=null) {
			Integer atchFileId = Optional.ofNullable(projectTaskSubmission.getAtchFile())
					.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
					.map(af -> {
						atchFileService.createAtchFile(af, saveFolder);
						return af.getAtchFileId();
					}).orElse(null);
			projectTaskSubmission.setAtchFileId(atchFileId);	
		}
		else {
			throw new PKNotFoundException("해당과제번호는 존재하지않습니다.");
		}
		
		return mapper.insertProjectTaskSubmission(projectTaskSubmission);
	}

	@Override
	public ProjectTaskSubmissionVO readProjectTaskSubmission(String tasksubNo) {
		// TODO Auto-generated method stub
		return mapper.selectProjectTaskSubmission(tasksubNo);
	}

	@Override
	public List<ProjectTaskSubmissionVO> readProjectTaskSubmissionList(String lectNo) {
		// TODO Auto-generated method stub
		return mapper.selectProjectTaskSubmissionList(lectNo);
	}

	@Override
	public int modifyProjectTaskSubmission(final ProjectTaskSubmissionVO projectTaskSubmission) {

		final ProjectTaskSubmissionVO saved = readProjectTaskSubmission(projectTaskSubmission.getTasksubNo());
		
		Integer newAtchFileId = Optional.ofNullable(projectTaskSubmission.getAtchFile())
				.filter(af -> af.getFileDetails() != null)
				.map(af ->mergeSavedDetailsAndNewDetails(saved.getAtchFile() , af))
				.orElse(null);
		
		projectTaskSubmission.setAtchFileId(newAtchFileId);
		
		return mapper.updateProjectTaskSubmission(projectTaskSubmission);
	}

	@Override
	public int removeProjectTaskSubmission(String tasksubNo) {
		ProjectTaskSubmissionVO projectTaskSubmission = mapper.selectProjectTaskSubmission(tasksubNo);
		
		Optional.ofNullable(projectTaskSubmission.getAtchFileId())
		.ifPresent(fid -> atchFileService.disableAtchFile(fid));
		
		return mapper.deleteProjectTaskSubmission(tasksubNo);
	}

	@Override
	public int updateGradeProjectTaskSubmission(ProjectTaskSubmissionVO projectTaskSubmission) {
		
		return mapper.updateGradeProjectTaskSubmission(projectTaskSubmission);
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

	@Override
	public ProjectTaskSubmissionVO checkSubmit(String teamCd) {
		
		return mapper.checkSubmit(teamCd);
	}

	@Override
	public int peerSubmit(final ProjectTaskSubmissionVO projectTaskSubmission,String stuId) {
		
		final ProjectTaskSubmissionVO saved = readProjectTaskSubmission(projectTaskSubmission.getTasksubNo());
		
		Integer newAtchFileId = Optional.ofNullable(projectTaskSubmission.getAtchFile())
				.filter(af -> af.getFileDetails() != null)
				.map(af ->mergeSavedDetailsAndNewDetails(saved.getAtchFile() , af))
				.orElse(null);
		
		projectTaskSubmission.setAtchFileId(newAtchFileId);
		
		int res = mapper.submitPeer(projectTaskSubmission);
		
		if(res>0) {
			int result = projectMemberservice.updatePeerYn(stuId);
			if(result<=0) {
				throw new BoardException("피어리뷰제출상태변경 실패!");
			}
		}
		
		return res;
	}

}
	


