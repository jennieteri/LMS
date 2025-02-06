package kr.or.ddit.yguniv.commons.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.yguniv.commons.dao.CommonCodeMapper;
import kr.or.ddit.yguniv.commons.service.CommonCodeServiceImpl;
import kr.or.ddit.yguniv.vo.ClassRoomVO;
import kr.or.ddit.yguniv.vo.CommonCodeVO;
import kr.or.ddit.yguniv.vo.SemesterVO;

@RestController
@RequestMapping("/commoncode")
public class CommonCodeController {
	@Autowired
	CommonCodeServiceImpl cocoService;
	
	@GetMapping("{parCocoCd}")
	public List<CommonCodeVO> getCommonCodeList(@PathVariable String parCocoCd){
		return cocoService.getCodeList(parCocoCd);
	}
	
	@GetMapping("classRoom")
	public List<ClassRoomVO> getClassRoomList(){
		return cocoService.getClassRoomList();
	}
	
	@GetMapping("semester")
	public List<SemesterVO> getSemesterList(Authentication authentication){
		Collection<? extends GrantedAuthority> role = authentication.getAuthorities();
		List<SemesterVO> semesterList = new ArrayList<>();
		if(role.contains(new SimpleGrantedAuthority("ROLE_PROFESSOR"))) {
			semesterList = cocoService.getSemesterList(authentication.getName());
		}else {
			semesterList = cocoService.getSemesterList(null);
		}
		return semesterList;
	}
}
