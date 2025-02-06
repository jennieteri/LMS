package kr.or.ddit.yguniv.commons.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.yguniv.vo.NoticeBoardVO;

@Mapper
public interface DateCheckMapper {
	public NoticeBoardVO getDate(@Param("cocoCd") String cocoCd);
}
