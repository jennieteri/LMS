package kr.or.ddit.yguniv.board.systemBoard.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.yguniv.annotation.RootContextWebConfig;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.paging.SimpleCondition;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.AtchFileVO;
import kr.or.ddit.yguniv.vo.SystemNoticeBoardVO;

@RootContextWebConfig
@Transactional
class SystemBoardServiceTest {
	
	@Autowired
	SystemBoardService service;
	
	SystemNoticeBoardVO boardWithFiles;
	SystemNoticeBoardVO boardWithNoFiles;
	
	
	@BeforeEach
	void beforeEach() throws IllegalAccessException, InvocationTargetException {
		boardWithFiles = new SystemNoticeBoardVO();
		boardWithNoFiles = new SystemNoticeBoardVO();
		boardWithFiles.setSnbTtl("새글 제목");
		boardWithFiles.setAdminId("관리자");
		boardWithFiles.setSnbCn("새글 내용");
		

		BeanUtils.copyProperties(boardWithFiles, boardWithNoFiles);

		AtchFileVO atchFile = new AtchFileVO();
		MockMultipartFile file1 = new MockMultipartFile("atchFile.fileDetails[0].boFiles", "test.jpg", "image/jpeg",
				ArrayUtils.EMPTY_BYTE_ARRAY);
		MockMultipartFile file2 = new MockMultipartFile("atchFile.fileDetails[1].boFiles", "test.jpg", "image/jpeg",
				ArrayUtils.EMPTY_BYTE_ARRAY);
		atchFile.setFileDetails(Arrays.asList(new AtchFileDetailVO(file1), new AtchFileDetailVO(file2)));

		assertDoesNotThrow(() -> {
			boardWithFiles.setAtchFile(atchFile);
			service.insertSystemBoard(boardWithFiles);
			
		});
		assertDoesNotThrow(() -> {
			boardWithNoFiles.setAtchFile(null);
			service.insertSystemBoard(boardWithNoFiles);
			
		});

	}
	

	@AfterEach
	void afterEach() {
		assertDoesNotThrow(() -> {
			Optional.ofNullable(boardWithFiles)
					.map(SystemNoticeBoardVO::getAtchFile)
					.map(AtchFileVO::getFileDetails)
					.map(List::stream).orElse(Stream.empty())
					.filter(fd -> fd.getFileStreCours() != null)
					.forEach(fd -> FileUtils.deleteQuietly(new File(fd.getFileStreCours())));
			Optional.ofNullable(boardWithNoFiles)
					.map(SystemNoticeBoardVO::getAtchFile)
					.map(AtchFileVO::getFileDetails)
					.map(List::stream).orElse(Stream.empty())
					.filter(fd -> fd.getFileStreCours() != null)
					.forEach(fd -> FileUtils.deleteQuietly(new File(fd.getFileStreCours())));
		});
	}
	


	@Test
	void testInsertSystemBoard() {
		
	}

	@Test
	void testSelectSystemBoard() {
		
		
		
		
		
		
	}

	@Test
	void testSelectList() {
		PaginationInfo<SystemNoticeBoardVO> paging = new PaginationInfo<>();
		paging.setCurrentPage(1);
		SimpleCondition simpleCondition = new SimpleCondition();
		simpleCondition.setSearchWord("수정");
		paging.setSimpleCondition(simpleCondition);
		assertDoesNotThrow(() -> service.selectList(paging));
	}

	@Test
	void testSelectListPaginationInfoOfSystemNoticeBoardVO() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateSystemBoard() {
		assertDoesNotThrow(() -> service.updateSystemBoard(boardWithNoFiles));

		assertNull(service.selectSystemBoard(boardWithNoFiles.getSnbNo()).getAtchFile());
	}

	@Test
	void testDeleteSystemBoard() {
		fail("Not yet implemented");
	}

}
