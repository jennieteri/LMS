package kr.or.ddit.yguniv.mypage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.vo.ActiveModuleVO;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.ModuleVO;

@Mapper
public interface MainUIMapper {
	public List<ActiveModuleVO> myModule(@Param("id") String id);
	public List<LectureVO> schedule(@Param("id") String id);
	public void deleteModule(@Param("id")String id);
	public void saveModule(@Param("id") String id, @Param("modList") List<ActiveModuleVO> modList);
	public List<ModuleVO> moduleList();
}
