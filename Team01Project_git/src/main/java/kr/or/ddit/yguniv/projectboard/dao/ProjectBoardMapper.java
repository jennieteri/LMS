package kr.or.ddit.yguniv.projectboard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.ProjectBoardVO;

@Mapper
public interface ProjectBoardMapper {
	
	/**게시글생성
	 * @return
	 */
	public int insertProjectBoard(ProjectBoardVO projectBoard);

	/**게시글수정
	 * @return
	 */
	public int updateProjectBoard(ProjectBoardVO projectBoard);

	/**상세조회
	 * @return
	 */
	public ProjectBoardVO selectProjectBoard(int pbNo);

	/**목록조회
	 * @return
	 */
	public List<ProjectBoardVO> selectProjectBoardList(@Param("paging") PaginationInfo<ProjectBoardVO> paging, @Param("teamCd") String teamCd);

	/**게시글삭제
	 * @return
	 */
	public int deleteProjectBoard(int pbNo);

	/**조회수증가
	 * @return
	 */
	public int incrementHit(int pbNo);

	/**목록 수 조회
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo<ProjectBoardVO> paging);
	
	
	/**공지설정된 게시글 유무확인
	 * @param teamCd
	 * @return
	 */
	public int checkDuplicate(String teamCd);
	
	/**공지게시글 가져오기
	 * @param teamCd
	 * @return
	 */
	public ProjectBoardVO selectNoti(String teamCd);
	
}
