package kr.or.ddit.yguniv.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of="stuId")
public class JobTestResultVO implements Serializable{
	
	@NotNull
	@Size(max = 10)
	private String stuId; //학생번호
	
	@NotNull
	private int jtrRea; //현실형점수 
	
	@NotNull
	private int jtrInq; //탐구형점수
	
	@NotNull
	private int jtrArt; //예술형점수
	
	@NotNull
	private int jtrSoc; //사회형점수
	
	@NotNull
	private int jtrEnt; //진취형점수
	
	@NotNull
	private int jtrCon; //관습형점수
	
	private Integer rnum; //페이징 순번
	
	private String code; //공통코드 (학년)
	
	@NotNull
	@DateTimeFormat(iso=ISO.DATE)
	private LocalDate jtrDt; //검사일자
	
	//학생 정보 가져오기
	private StudentVO student;
	
}
