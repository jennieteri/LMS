package kr.or.ddit.yguniv.projectboard.service;

import java.util.List;


import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.ProjectBoardVO;


public interface ProjectBoardService {
	
	/**게시글생성
	 * @return
	 */
	public int createProjectBoard(ProjectBoardVO projectBoard);

	/**게시글수정
	 * @return
	 */
	public int modifyProjectBoard(ProjectBoardVO projectBoard);

	/**상세조회
	 * @return
	 */
	public ProjectBoardVO readProjectBoard(int pbNo);

	/**목록조회
	 * @return
	 */
	public List<ProjectBoardVO> readProjectBoardList(PaginationInfo<ProjectBoardVO> paging,String teamCd);

	/**게시글삭제
	 * @return
	 */
	public int removeProjectBoard(int pbNo);
	
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
	
	
	/**공지로 설정된 게시글여부확인
	 * @param teamCd
	 * @return
	 */
	public int checkDuplicate(String teamCd);
	
	/**팀게시판 공지글가져오기
	 * @param teamCd
	 * @return
	 */
	public ProjectBoardVO readNoti(String teamCd);
}
