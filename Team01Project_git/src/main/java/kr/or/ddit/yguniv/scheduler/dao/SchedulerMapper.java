package kr.or.ddit.yguniv.scheduler.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.vo.PersonVO;

@Mapper
public interface SchedulerMapper {
	
	public PersonVO selectPersonData(String id);

}
