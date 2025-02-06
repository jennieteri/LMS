package kr.or.ddit.yguniv.attendee.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.vo.AttendeeVO;

@Mapper
public interface AttendeeMapper {
	public int countAttendee(String lectNo);
	
	public List<AttendeeVO> selectAttendeeListWithLecture(String lectNo);
	
	public List<AttendeeVO> selectAttendeeForProject(String lectNo);
	
	public List<AttendeeVO> selectAttendeeForProjectNoTeam(@Param("lectNo")String lectNo, @Param("taskNo")String taskNo);
	
}
