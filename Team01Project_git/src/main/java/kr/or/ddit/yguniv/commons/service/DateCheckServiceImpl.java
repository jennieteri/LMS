package kr.or.ddit.yguniv.commons.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.commons.dao.DateCheckMapper;
import kr.or.ddit.yguniv.vo.NoticeBoardVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DateCheckServiceImpl {

	private final DateCheckMapper dao;
	
	public NoticeBoardVO getDate(String cocoCd){
		
		return dao.getDate(cocoCd);
	}

	public boolean dateCheck(NoticeBoardVO notiVo) {
	    boolean tf = false;
	    LocalDate date = LocalDate.now();
	    LocalDate start = notiVo.getNtcDt();
	    LocalDate end = notiVo.getNtcEt();

	    if ((start.isBefore(date) || start.isEqual(date)) && (end.isAfter(date) || end.isEqual(date))) {
	        tf = true;
	    }

	    return tf;
	}

	
	
	
	
	
	
	
	
}
