package kr.or.ddit.yguniv.projectTask.service;

import java.util.List;

import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.ProjectTaskVO;

public interface ProjectTaskService {
	
	/** 조별과제프로젝트 생성
	 * @param projectTask
	 */
	public void createProjectTask(ProjectTaskVO projectTask);
	
	/** 조별과제프로젝트 단건조회(상세조회)
	 * @param taskNo
	 * @return
	 */
	public ProjectTaskVO readProjectTask(String taskNo);
	
	/** 해당강의 조별과제프로젝트 다건조회(목록조회)
	 * @param lectNo
	 * @return
	 */
	public List<ProjectTaskVO> readProjectTaskList(String lectNo);
	
	/** 조별과제프로젝트수정(주제/내용/마감일/배점)
	 * @param projectTask
	 */
	public void modifyProjectTask(ProjectTaskVO projectTask);
	
	/** 조별과제 삭제(상태 N값으로 변경)
	 * @param taskNo
	 */
	public void removeProjectTask(String taskNo);
	
	/** 파일 다운로드
	 * @param atchFileId
	 * @param fileSn
	 * @return
	 */
	public AtchFileDetailVO download(int atchFileId, int fileSn);
	/** 파일 한건 삭제
	 * @param atchFileId
	 * @param fileSn
	 */
	public void removeFile(int atchFileId, int fileSn);
	
	
	
}
