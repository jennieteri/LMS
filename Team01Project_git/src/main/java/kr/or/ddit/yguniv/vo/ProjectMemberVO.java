package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.Max;

import lombok.Data;

@Data
public class ProjectMemberVO implements Serializable {
	
	private String nm;
	
	private String lectNo;
	
	private String stuId;
	
	private String teamCd;
	
	private String taskNo;
	
	private String leadYn;
	
	@Max(100)
	private Integer peerScore;
	
	private String PeerYn;
	
	//has a
	
	ProjectTeamVO projectTeam;
	
	//has a
	AttendeeVO attendee;
	
	//has a
	StudentVO studentVO;
}
