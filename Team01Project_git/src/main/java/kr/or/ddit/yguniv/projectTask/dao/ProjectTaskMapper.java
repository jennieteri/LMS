package kr.or.ddit.yguniv.projectTask.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.ProjectTaskVO;

@Mapper
public interface ProjectTaskMapper {
	/**프로젝트과제생성
	 * @param task
	 * @return
	 */
	public int insertProjectTask(ProjectTaskVO task);
	/**프로젝트단건조회
	 * @param taskNo
	 * @return
	 */
	public ProjectTaskVO selectProjectTask(String taskNo);
	/**프로젝트목록조회
	 * @return
	 */
	public List<ProjectTaskVO> selectProjectTasklist(String lectNo);
	/**프로젝트수정
	 * @param task
	 * @return
	 */
	public int updateProjectTask(ProjectTaskVO task);
	/**프로젝트삭제	진행여부 N 변경
	 * @param taskNo
	 * @return
	 */
	public int deleteProjectTask(String taskNo);
	
}
