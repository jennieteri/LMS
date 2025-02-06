package kr.or.ddit.yguniv.noticeboard.service;

import java.util.List;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.NoticeBoardVO;

public interface NoticeBoardService {
	/** 신규 글 생성
	 * @param board
	 */
	public void createNoticeBoard(NoticeBoardVO board);
	/**특정 글 조회
	 * @param ntcCd
	 * @return
	 */
	public NoticeBoardVO readNoticeBoard(int ntcCd);
	/** 페이징 처리 없는 목록 조회
	 * @return
	 */
	public List<NoticeBoardVO> readNoticeBoardList();
	/** 페이징 목록 조회
	 * @param paginationInfo
	 * @return
	 */
	public List<NoticeBoardVO> readNoticeBoardListPaging(PaginationInfo<NoticeBoardVO> paginationInfo);
	/** 게시글 수정
	 * @param board
	 */
	public void modifyNoticeBoard(NoticeBoardVO board);
	/** 게시글 삭제
	 * @param ntcCd
	 */
	public void removeNoticeBoard(int ntcCd);
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
	
	public List<String> getStudentList();
	
	
	
}
