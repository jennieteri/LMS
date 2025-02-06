package kr.or.ddit.yguniv.commons.mycalendar.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.commons.mycalendar.dao.MyCalendarMapper;
import kr.or.ddit.yguniv.jobboard.dao.JobBoardMapper;
import kr.or.ddit.yguniv.noticeboard.dao.NoticeBoardMapper;
import kr.or.ddit.yguniv.vo.JobBoardVO;
import kr.or.ddit.yguniv.vo.MyCalendarVO;
import kr.or.ddit.yguniv.vo.NoticeBoardVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyCalendarServiceImpl implements MyCalendarService {
	private final MyCalendarMapper mapper;
	private final JobBoardMapper jobBoardMapper;
	private final NoticeBoardMapper noticeBoardMapper;
	
	@Override
	public int createMyCalendar(MyCalendarVO mycalendar) {
		
		return mapper.insertMyCalendar(mycalendar);
	}

	@Override
	public MyCalendarVO readMyCalendar(int myCalendarNo) {
		// TODO Auto-generated method stub
		return mapper.selectMyCalendar(myCalendarNo);
	}

	@Override
	public List<MyCalendarVO> readMyCalendarList(String prsId) {
		// TODO Auto-generated method stub
		return mapper.selectMyCalendarList(prsId);
	}

	@Override
	public int modifyMyCalendar(MyCalendarVO mycalendar) {
		//연동 취소했던것 다시 등록하기
		
		return mapper.updateMyCalendar(mycalendar);
	}

	@Override
	public int deleteMyCalendar(String myCalendarNo) {
		// TODO Auto-generated method stub
		return mapper.deleteMyCalendar(myCalendarNo);
	}

	@Override
	public int linkedMycalendar(MyCalendarVO mycalendar, String type) {
		//캘린더 연동여부 체크
		Map<String,Object> param = new HashMap<>();
		param.put("id", mycalendar.getPrsId());
		param.put("no", mycalendar.getBoardNo());
		param.put("type", type);
		//중복체크
		boolean duplicatedYn = mapper.checkDuplicate(mycalendar)>0;
		if(duplicatedYn) {
			return modifyMyCalendar(mapper.selectMyCalendarWithBoNo(mycalendar));
		}
		
		MyCalendarVO addMyCalendar = new MyCalendarVO();
		addMyCalendar.setPrsId(mycalendar.getPrsId());
		
		if(type=="job") {
			String jobNo = mycalendar.getBoardNo();
			JobBoardVO jobBoard = jobBoardMapper.selectJobBoard(jobNo);
			
			
			addMyCalendar.setMyCalendarCd("A003");
			addMyCalendar.setBoardNo(jobNo);
			//제목
			addMyCalendar.setMyCalendarTitle(jobBoard.getJobNm());
			//내용
			addMyCalendar.setMyCalendarContent(jobBoard.getJobContent());
			//시작일
			addMyCalendar.setMyCalendarSd(jobBoard.getJobDt());
			//마감일
			addMyCalendar.setMyCalendarEd(jobBoard.getJobEt());
		}
		
		if(type=="noti") {
			String ntcCd = mycalendar.getBoardNo();
			NoticeBoardVO noticeBoard = noticeBoardMapper.selectNoticeBoard(Integer.parseInt(ntcCd));
			
			addMyCalendar.setMyCalendarCd("A002");
			addMyCalendar.setBoardNo(ntcCd);
			//제목
			addMyCalendar.setMyCalendarTitle(noticeBoard.getNtcNm());
			//내용
			addMyCalendar.setMyCalendarContent(noticeBoard.getNtcDesc());
			//시작일
			addMyCalendar.setMyCalendarSd(noticeBoard.getNtcDt());
			//마감일
			addMyCalendar.setMyCalendarEd(noticeBoard.getNtcEt());
			
		}
		
		
		return mapper.insertMyCalendar(addMyCalendar);
	}

	@Override
	public int deleteLinked(MyCalendarVO mycalendar, String type) {
		
		if(type=="job") {
			mycalendar.setMyCalendarCd("A003");
		}
		if(type=="noti") {
			mycalendar.setMyCalendarCd("A002");
		}
		
		MyCalendarVO deleteMyCal = mapper.selectMyCalendarWithBoNo(mycalendar);
		
		
		return mapper.deleteMyCalendar(deleteMyCal.getMyCalendarNo());
	}

	@Override
	public boolean checkMyCal(Map<String, Object> param) {
		MyCalendarVO myCalendar = new MyCalendarVO();
		MyCalendarVO defaultCalendar = new MyCalendarVO();
		
		String id = (String)param.get("id");
		String no = (String)param.get("no");
		String type = (String)param.get("type");
		
		myCalendar.setBoardNo(no);
		myCalendar.setPrsId(id);
		
		if(type=="job") {
			myCalendar.setMyCalendarCd("A003");
		}
		if(type=="noti") {
			myCalendar.setMyCalendarCd("A002");
		}
		MyCalendarVO resultMycal = Optional.ofNullable(mapper.selectMyCalendarWithBoNo(myCalendar))
						.orElse(defaultCalendar);
		
		
		return !StringUtils.isEmpty(resultMycal.getMyCalendarNo());
	}

}
