package kr.or.ddit.yguniv.login.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.yguniv.login.dao.PersonDAO;
import kr.or.ddit.yguniv.vo.PersonVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonLoginServiceImpl implements PersonService {

	private final PersonDAO dao;

	@Transactional
	@Override
	public int mergeIntoPersonVisit(String date) {
		  LocalDate today = LocalDate.now();
		    String formattedDate = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		
		return dao.mergeIntoPersonVisit(formattedDate);
	}

	
	
}
