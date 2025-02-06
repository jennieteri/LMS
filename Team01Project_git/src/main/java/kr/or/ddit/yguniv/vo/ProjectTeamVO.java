package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "teamCd")
public class ProjectTeamVO implements Serializable{
	// 팀명이 있어도 좋을 것 같다.
	// 팁번호(식별자)는 시퀀스로 처리하면서 노출되는 것은 팀명으로 노출하는 방식
	private int rnum;
	
	private String teamStatus;
	
	@NotBlank
	@Size(max = 10)
	private String teamCd;
	@NotBlank
	@Size(max = 20)
	private String teamPurpo;
	@NotBlank
	@Size(max = 3000)
	private String teamNotes;
	
	private String teamEvyn;
	
	private String teamNm;
	
	private Integer teamProge;
	
	private String taskNo;
	//has a 
	private ProjectTaskVO projectTask;
	
	//has a
	private ProjectTaskSubmissionVO projectTaskSubmission;
	
	private List<ProjectMemberVO> teamMember;
}
