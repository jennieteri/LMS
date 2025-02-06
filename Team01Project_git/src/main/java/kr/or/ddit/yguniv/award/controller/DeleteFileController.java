package kr.or.ddit.yguniv.award.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.yguniv.award.service.AwardRecommendService;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
@Controller
@RequestMapping("/recommendAward/{shapDocNo}/professor/atch")
public class DeleteFileController {
	
	@Autowired
	private AwardRecommendService service;


		@DeleteMapping("{atchFileId}/{fileSn}")
		@ResponseBody
		public Map<String, Object> deleteAttatch(@PathVariable int atchFileId, @PathVariable int fileSn) {
			service.removeFile(atchFileId, fileSn);
			return Collections.singletonMap("success", true);
		}

	}

