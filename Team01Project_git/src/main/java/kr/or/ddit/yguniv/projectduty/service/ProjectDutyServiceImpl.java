package kr.or.ddit.yguniv.projectduty.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.noticeboard.exception.BoardException;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.projectduty.dao.ProjectDutyMapper;
import kr.or.ddit.yguniv.vo.ProjectDutyVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectDutyServiceImpl implements ProjectDutyService {
	private final ProjectDutyMapper mapper;
	
	
	@Override
	public String createProjectDuty(ProjectDutyVO projectDuty) {
		try {
			int res = mapper.insertProjectDuty(projectDuty);
			if(res>0) {
				return projectDuty.getDutyNo();
			}
			return null;
		}catch(RuntimeException e){
			throw new BoardException("일감생성실패!", e);
		}
		
	}

	@Override
	public ProjectDutyVO readProjectDuty(String dutyNo) {
		try {
			ProjectDutyVO projectDuty = mapper.selectProjectDuty(dutyNo);
			return projectDuty;
		}catch(RuntimeException e){
			throw new BoardException(String.format("%d 번 글이 없음.", dutyNo));
		}
		 
	}

	@Override
	public List<ProjectDutyVO> readProjectDutyList(PaginationInfo<ProjectDutyVO> paging, String teamCd) {
		paging.setTotalRecord(mapper.selectTotalRecord(paging));
		List<ProjectDutyVO> projectDutyList = mapper.selectProjectDutylist(paging, teamCd);
		
		return projectDutyList;
	}

	@Override
	public int modifyProjectDuty(ProjectDutyVO projectDuty) {

		return mapper.updateProjectDuty(projectDuty);
	}

	@Override
	public int removeProjectDuty(String dutyNo) {

		return mapper.deleteProjectDuty(dutyNo);
	}

	@Override
	public List<ProjectDutyVO> selectProjectDutylistNonPaging(String dutyTeam) {
		
		return mapper.selectProjectDutylistNonPaging(dutyTeam);
	}
	
}
