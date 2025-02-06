package kr.or.ddit.yguniv.tuition.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.paging.DataTablesPaging;
import kr.or.ddit.yguniv.tuition.dao.TuitionMapper;
import kr.or.ddit.yguniv.vo.TuitionVO;

@Service
public class TuitionServiceImpl {
	@Autowired
	private TuitionMapper tuiMapper;
	
	/**
	 * R-등록금 납부상태 목록 조회
	 */
	public List<TuitionVO> selectTuitionList(DataTablesPaging<TuitionVO> paging){
		paging.setRecordsTotal(tuiMapper.selectTotalRecord());
		paging.setRecordsFiltered(tuiMapper.selectPagingTotalRecord(paging));
		return tuiMapper.selectTuitionList(paging);
	}
	/**
	 * R-등록금 납부상태 상세 조회
	 */
	public TuitionVO selectTuition(TuitionVO tuitionVO) {
		return tuiMapper.selectTuition(tuitionVO);
	}
	/**
	 * U-등록금 납부상태 수정
	 */
	public int updateTuition(TuitionVO tuitionVO) {
		return tuiMapper.updateTuition(tuitionVO);
	}
}
