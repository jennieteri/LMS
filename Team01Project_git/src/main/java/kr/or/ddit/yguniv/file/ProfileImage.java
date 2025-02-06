package kr.or.ddit.yguniv.file;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

public class ProfileImage {
	// 이미지 압축 메서드 (Thumbnailator 사용)
    public static String imgToBase64(MultipartFile image) throws IOException {
    	try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
    		Thumbnails.of(image.getInputStream())
		    		.size(180, 240)  // 최대 크기 설정
		    		.outputQuality(0.5)  // 품질 설정 (0.0 ~ 1.0)
		    		.keepAspectRatio(true)  // 비율 유지 설정
		    		.toOutputStream(outputStream);
    		
    		return Base64.getEncoder().encodeToString(outputStream.toByteArray());  // 압축된 이미지를 Base64로 반환
    	}
    }
}
