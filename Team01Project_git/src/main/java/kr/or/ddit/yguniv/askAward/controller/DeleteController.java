package kr.or.ddit.yguniv.askAward.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.yguniv.askAward.service.AskAwardService;

@Controller
@RequestMapping("/askAward/{shapDocNo}/student/atch")
public class DeleteController {
	
	@Autowired
	private AskAwardService service;
	
	@DeleteMapping("{atchFileId}/{fileSn}")
	@ResponseBody
	public Map<String, Object> deleteAttatch(@PathVariable int atchFileId, @PathVariable int fileSn) {
		service.removeFile(atchFileId, fileSn);
		return Collections.singletonMap("success", true);
	}

	
	

}
