package kr.or.ddit.yguniv.noticeboard.controller;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/imageUpload")
public class ImageUploader {

	@Value("#{dirInfo.prodImages}")
    private String uploadPath;
	
	@Autowired
    private ServletContext servletContext;
	
	@PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("upload") MultipartFile file) {
		String realPath = servletContext.getRealPath(uploadPath);
		if (realPath == null) {
            return ResponseEntity.internalServerError().body("{\"error\": \"Invalid upload path\"}");
        }
		
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("{\"error\": \"No file uploaded\"}");
        }

        try {
            // 저장 경로 생성
            File uploadDir = new File(realPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            // 고유 파일 이름 생성
            String originalFileName = file.getOriginalFilename();
            String fileName = UUID.randomUUID() + "_" + originalFileName;

            // 파일 저장
            File savedFile = new File(uploadDir, fileName);
            file.transferTo(savedFile);

            // 반환할 파일 URL 생성
            String fileUrl = servletContext.getContextPath() +uploadPath + fileName;
            return ResponseEntity.ok(Collections.singletonMap("url", fileUrl));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("{\"error\": \"File upload failed\"}");
        }
    }
}
