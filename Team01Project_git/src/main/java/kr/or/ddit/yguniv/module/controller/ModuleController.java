package kr.or.ddit.yguniv.module.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.yguniv.commons.dao.CommonCodeMapper;

@RestController
@RequestMapping("/module")
public class ModuleController {
	@Autowired
	private CommonCodeMapper cocoMapper;
	
	@GetMapping("schedule")
	public Map<String, Object> schedule() {
		Map<String, Object> map = new HashMap<>();
		// 요일 리스트
		map.put("dotwList", cocoMapper.getCodeList("DOTW"));
		// 교시 리스트
		map.put("etimeList", cocoMapper.getEducationTimeTableCodeList());
		return map;
	}
}
