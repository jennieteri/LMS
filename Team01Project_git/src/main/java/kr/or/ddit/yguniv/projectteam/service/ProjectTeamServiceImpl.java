package kr.or.ddit.yguniv.projectteam.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.attendee.dao.AttendeeMapper;
import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.noticeboard.exception.BoardException;
import kr.or.ddit.yguniv.projectMember.dao.ProjectMemberMapper;
import kr.or.ddit.yguniv.projectMember.service.ProjectMemberService;
import kr.or.ddit.yguniv.projectTask.service.ProjectTaskService;
import kr.or.ddit.yguniv.projectteam.dao.ProjectTeamMapper;
import kr.or.ddit.yguniv.vo.AttendeeVO;
import kr.or.ddit.yguniv.vo.ProjectMemberVO;
import kr.or.ddit.yguniv.vo.ProjectTaskVO;
import kr.or.ddit.yguniv.vo.ProjectTeamFormVO;
import kr.or.ddit.yguniv.vo.ProjectTeamVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectTeamServiceImpl implements ProjectTeamService {
	private final ProjectTeamMapper mapper;
	private final ProjectTaskService projectTaskService;
	private final AttendeeMapper attendeeMapper;
	private final ProjectMemberService projectMemberService;
	
	@Override
	public int createProjectTeam(ProjectTeamFormVO teamForm) {
		//팀갯수를 받아서 해당 갯수만큼 팀 생성하기
		int teamCount = Integer.parseInt(teamForm.getTeamCount());
		int createdTeams = 0;
		
		try {
			//해당 프로젝트과제 가져오기
			ProjectTaskVO task = projectTaskService.readProjectTask(teamForm.getTaskNo());
			// 팀생성 필수값 프로젝트 주제,프로젝트내용,프로젝트시작일,프로젝트종료일
			for(int i=0; i<teamCount; i++) {
				ProjectTeamVO projectTeam = new ProjectTeamVO();
				try {
						projectTeam.setProjectTask(task);
						//주제
						projectTeam.setTeamPurpo(task.getTaskTitle());
						//내용
						projectTeam.setTeamNotes(task.getTaskNotes());
						//프로젝트과제번호
						projectTeam.setTaskNo(task.getTaskNo());
						
						projectTeam.setTeamNm(String.format("%s (%d)팀", task.getTaskTitle(), i));
						
						createdTeams += mapper.insertProjectTeam(projectTeam);
						
				}catch(RuntimeException e) {
					throw new BoardException("팀생성중 데이터오류",e);
				}
			}
		}catch(RuntimeException e){
			throw new PKNotFoundException("해당과제는 존재하지않습니다.",e);
		}
		
		if(createdTeams!=teamCount) {
			throw new BoardException("오류!!! 팀갯수만큼 생성되지않음!");
		}
		
		return createdTeams;
	}

	@Override
	public ProjectTeamVO readProjectTeam(String teamCd) {
		try {
			ProjectTeamVO projectTeam = mapper.selectProjectTeam(teamCd);
			return projectTeam;
			
		}catch(RuntimeException e) {
			throw new BoardException("해당 팀번호의 팀은 존재하지않습니다.",e);
		}
		
	}

	@Override
	public List<ProjectTeamVO> readProjectTeamList(String taskNo) {
		if(taskNo==null||taskNo.isEmpty()) {
			throw new BoardException("과제번호가 입력되지않았습니다.");
		}
		
		return mapper.selectProjectTeamlistWithTask(taskNo);
	}

	@Override
	public int modifyProjectTeam(ProjectTeamVO projectTeam) {
		
		return mapper.updateProjectTeam(projectTeam);
	}

	@Override
	public int removeProjectTeam(String teamCd) {
		try {
			return mapper.deleteProjectTeam(teamCd);
			
		}catch(RuntimeException e) {
			throw new BoardException("해당 팀번호의 팀은 존재하지않습니다.",e);
		}
	}

	@Override
	public int updateStatus(String teamCd) {
		try {
			ProjectTeamVO projectTeam = mapper.selectProjectTeam(teamCd);
			String status= projectTeam.getTeamStatus();
			if(status.equalsIgnoreCase("Y")) {
				status = "N";
			}else {
				status = "Y";
			}
			projectTeam.setTeamStatus(status);
			
			return mapper.updateStatus(projectTeam);
			
		}catch(RuntimeException e) {
			throw new BoardException("해당 팀번호의 팀은 존재하지않습니다.",e);
		}
	}

	@Override
	public int updateEvyn(String teamCd) {
		try {
			return mapper.updateEvyn(teamCd);
			
		}catch(RuntimeException e) {
			throw new BoardException("해당 팀번호의 팀은 존재하지않습니다.",e);
		}
	}

	@Override
	public int updateProge(ProjectTeamVO projectTeam) {
		
		return mapper.updateProge(projectTeam);
	}

	@Override
	public int countProjectTeamNoMember(String taskNo) {

		return mapper.countProjectTeamNoMember(taskNo);
	}

	@Override
	public int updateTeamNm(ProjectTeamVO projectTeam) {
		
		return mapper.updateTeamNm(projectTeam);
	}

	@Override
	public List<ProjectTeamVO> autoAllot(ProjectTeamFormVO projectTeamForm) {
		// lectNo 강의번호 , taskNo 과제번호, teamCount 팀갯수,
		String taskNo = projectTeamForm.getTaskNo();
		String lectNo = projectTeamForm.getLectNo();
		// 팀생성 필수값 과제번호, 프로젝트 주제,프로젝트내용
		
		// memberCount 팀당 인원수, allowYn 작은팀허용, totalCount 팀생성시 필요 총 인원 수
		String allowYn = projectTeamForm.getAllowYn();
		int memberCount = Integer.parseInt(projectTeamForm.getMemberCount());
		int totalCount = projectTeamForm.getTotalCount();
		
		// 수강생목록(팀배정되지않은) 가지고오기 attendeemapper
		List<AttendeeVO> noteamAttendeeList = 
				attendeeMapper.selectAttendeeForProjectNoTeam(lectNo,taskNo);
		
		// 작은 팀 비허용("N") 처리
		if (allowYn.equalsIgnoreCase("N") && totalCount > noteamAttendeeList.size()) {
		       throw new BoardException("남은 수강생 수가 부족합니다. 작은 팀 허용을 선택해주세요.");
	    }
		
		// 수강생 랜덤순서로 만들기
		Collections.shuffle(noteamAttendeeList);
		
		// 팀리스트 만들기(해당과제에 생성된 팀리스트 가져옴 멤버자동배정은 이미 생성된 팀에 넣어주는거임.)
		List<ProjectTeamVO> teamList = mapper.selectProjectTeamlistWithTask(taskNo);
		
		// AttendeeVO => projectMemberVO로 변환시켜서 teamList에 들어있는 projectTeamVO에 넣어줘야함
		int index = 0;
		for (AttendeeVO attendee : noteamAttendeeList) {
	        if (index >= teamList.size()) break; // 팀이 초과되지 않도록 방지

	        ProjectTeamVO currentTeam = teamList.get(index);

	        // 멤버 생성 및 설정
	        ProjectMemberVO projectMember = new ProjectMemberVO();
	        projectMember.setLectNo(attendee.getLectNo());
	        projectMember.setStuId(attendee.getStuId());
	        projectMember.setTaskNo(taskNo);
	        projectMember.setTeamCd(currentTeam.getTeamCd());

	        // 첫 번째 멤버를 팀장으로 설정
	        List<ProjectMemberVO> currentTeamMembers = projectMemberService.readProjectMemberList(currentTeam.getTeamCd());
	        if (currentTeamMembers.isEmpty()) {
	            projectMember.setLeadYn("Y"); // 팀장
	        } else {
	            projectMember.setLeadYn("N"); // 일반 멤버
	        }

	        // 멤버 저장
	        projectMemberService.createProjectMember(projectMember);

	        // 팀 멤버가 가득 찼다면 다음 팀으로 이동
	        if (currentTeamMembers.size() + 1 >= memberCount) {
	            index++;
	        }
	    }
		// 작은팀 허용시 =='Y' 처리
		if(allowYn.equalsIgnoreCase("Y")) {
			for (AttendeeVO attendee : noteamAttendeeList.subList(index * memberCount, noteamAttendeeList.size())) {
	            ProjectMemberVO projectMember = new ProjectMemberVO();
	            projectMember.setLectNo(attendee.getLectNo());
	            projectMember.setStuId(attendee.getStuId());
	            projectMember.setTaskNo(taskNo);

	            ProjectTeamVO targetTeam = teamList.get(index % teamList.size());
	            projectMember.setTeamCd(targetTeam.getTeamCd());
	            projectMember.setLeadYn("N"); // 일반 멤버로 설정

	            projectMemberService.createProjectMember(projectMember);
	        }
		}
		
		return mapper.selectProjectTeamlistWithTask(taskNo);
	}

	@Override
	public int resetTeamMember(String taskNo) {
		// 팀초기화
		return mapper.resetTeamMember(taskNo);
	}

}
