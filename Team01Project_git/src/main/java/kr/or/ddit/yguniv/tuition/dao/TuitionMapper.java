package kr.or.ddit.yguniv.tuition.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.yguniv.paging.DataTablesPaging;
import kr.or.ddit.yguniv.vo.TuitionVO;

@Mapper
public interface TuitionMapper {
	public int selectTotalRecord();
	public int selectPagingTotalRecord(DataTablesPaging<TuitionVO> paging);
	/**
	 * R-등록금 납부상태 목록 조회
	 */
	public List<TuitionVO> selectTuitionList(DataTablesPaging<TuitionVO> paging);
	/**
	 * R-등록금 납부상태 상세 조회
	 */
	public TuitionVO selectTuition(TuitionVO tuitionVO);
	/**
	 * U-등록금 납부상태 수정
	 */
	public int updateTuition(TuitionVO tuitionVO);
}
