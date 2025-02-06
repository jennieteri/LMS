package kr.or.ddit.yguniv.professor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.ProfessorVO;
@Mapper
public interface professorMapper {
	
	public List<ProfessorVO> selectProfessorList();

}
