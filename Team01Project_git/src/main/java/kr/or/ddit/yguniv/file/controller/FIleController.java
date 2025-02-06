package kr.or.ddit.yguniv.file.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.yguniv.file.service.FileServiceImpl;

@Controller
//@RequestMapping("/upload")
//@RequestMapping()
public class FIleController {
	
//	@Autowired
//	private FileServiceImpl fileService;
	
//	@PostMapping("/image")
//    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
//        try {
//            // 파일 업로드 처리
//            String imageUrl = fileService.upload(file);
//            return ResponseEntity.ok(imageUrl);  // 업로드된 파일의 URL 반환
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("File upload failed");
//        }
//    }
	
	
//	// 예시 (Spring Controller)
//	@RequestMapping("/uploadImage")
//	public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
//	    String fileName = fileService.upload(file); // 파일 업로드 처리
//	    String imageUrl = "/resources/prodImages/" + fileName;
//	    return ResponseEntity.ok(imageUrl);  // 업로드된 파일 URL 반환
//	}
}
