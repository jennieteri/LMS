package kr.or.ddit.yguniv.vo;

import java.io.Serializable;

import javax.validation.constraints.Max;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "dutyNo")
public class ProjectDutyVO implements Serializable{
	
	private int rnum;
	
	private String dutyTeam;
	
	private String dutyNo;
	
	private String dutyId;
	
	private String dutyCn;
	@Max(100)
	private Integer dutyPrgsRtprgs;
	
	private String dutyTitle;
	
	private String dutyDate;
	
	//has a
	private ProjectMemberVO projectMember;
}