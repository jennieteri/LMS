package kr.or.ddit.yguniv.file;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import kr.or.ddit.yguniv.annotation.RootContextWebConfig;
import kr.or.ddit.yguniv.student.dao.StudentMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RootContextWebConfig
class ProfileImageTest {
	@Autowired
	StudentMapper mapper;
	
	@Test
	void testImgToBase64() throws IOException {
		File img = new File("C:\\Users\\PC_08\\Desktop\\더미사진\\KakaoTalk_20250104_141554696.jpg");
		FileInputStream ins = new FileInputStream(img);
		if(img.exists()) {
			String base64 = ProfileImage.imgToBase64(new MockMultipartFile(img.getName(), img.getName(), "image/png", ins));
			assertEquals(1, mapper.insertImg(base64));
		}
	}

}
