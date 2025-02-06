package kr.or.ddit.yguniv.file.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

public class FileServiceImpl {
	
//	@Value("${file.upload-dir}")  // 파일 업로드 경로를 application.properties에서 설정
//    private String uploadDir;
//
//    public String upload(MultipartFile file) throws IOException {
//        // 파일 이름을 고유하게 만들기 위해 UUID 사용
//        String originalFileName = file.getOriginalFilename();
//        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
//        String newFileName = UUID.randomUUID().toString() + fileExtension;
//
//        // 파일을 지정된 디렉토리에 저장
//        File targetFile = new File(uploadDir + File.separator + newFileName);
//        file.transferTo(targetFile);
//
//        // 저장된 파일의 상대 경로를 반환
//        return "/resources/prodImages/" + newFileName;
//    }
}
