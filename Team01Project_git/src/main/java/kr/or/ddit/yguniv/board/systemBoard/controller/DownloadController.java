package kr.or.ddit.yguniv.board.systemBoard.controller;

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

import kr.or.ddit.yguniv.board.systemBoard.service.SystemBoardService;
import kr.or.ddit.yguniv.noticeboard.service.NoticeBoardService;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;

@Controller
@RequestMapping("/systemBoard/{snbNo}/atch")
public class DownloadController {
	
	@Autowired
	private SystemBoardService service;

	@GetMapping("{atchFileId}/{fileSn}")
	public ResponseEntity<Resource> download(AtchFileDetailVO target) throws IOException {
		AtchFileDetailVO atch = service.download(target.getAtchFileId(), target.getFileSn());

		Resource savedFile = atch.getSavedFile();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentLength(atch.getFileSize());
		ContentDisposition disposition = ContentDisposition.attachment()
				.filename(atch.getOrignlFileNm(), Charset.forName("UTF-8")).build();
		headers.setContentDisposition(disposition);
		return ResponseEntity.ok().headers(headers).body(savedFile);
	}

	@DeleteMapping("{atchFileId}/{fileSn}")
	@ResponseBody
	public Map<String, Object> deleteAttatch(@PathVariable int atchFileId, @PathVariable int fileSn) {
		service.removeFile(atchFileId, fileSn);
		return Collections.singletonMap("success", true);
	}

}
