package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProjectTeamFormVO implements Serializable{
	//강의번호를 통해 Attendee가져오기
	private String lectNo;
	//해당과제의 프로젝트팀 만들기
	private String taskNo;
	//팀 수 
	private String teamCount;
	//팀원 수
	private String memberCount;
	
	//작은 팀 허용여부
	private String allowYn;
	
	//필요 총 인원 수
	private int totalCount;
	
	//총 인원 수 가져올 때 처리
	public int getTotalCount() {
		int totalCount = Integer.parseInt(memberCount) * Integer.parseInt(teamCount);
				
		return totalCount;
	}
	//------------------------autoCreate 끝---------------------
	
	//수동생성을 위한 학번받기 lectNo도 필요
	private String stuId;
}
