package kr.or.ddit.yguniv.noticeboard.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
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
import kr.or.ddit.yguniv.vo.NoticeBoardVO;


@RootContextWebConfig
@Transactional
class NoticeBoardServiceImplTest {

	@Autowired
	NoticeBoardService service;
	
	NoticeBoardVO boardWithFiles;
	NoticeBoardVO boardWithNoFiles;
	LocalDate date= LocalDate.now();
	
	@BeforeEach
	void beforeEach() throws IllegalAccessException, InvocationTargetException {
		boardWithFiles = new NoticeBoardVO();
		boardWithNoFiles = new NoticeBoardVO();
		boardWithFiles.setPrsId("2024200001");
		boardWithFiles.setNtcNm("제목");
		boardWithFiles.setNtcDesc("내용");
		boardWithFiles.setNtcYn("N");
		boardWithFiles.setNtcDt(date);
		boardWithFiles.setNtcEt(date);
		
		BeanUtils.copyProperties(boardWithFiles, boardWithNoFiles);

		AtchFileVO atchFile = new AtchFileVO();
		MockMultipartFile file1 = new MockMultipartFile("atchFile.fileDetails[0].uploadFile", "test.jpg", "image/jpeg",
				ArrayUtils.EMPTY_BYTE_ARRAY);
		MockMultipartFile file2 = new MockMultipartFile("atchFile.fileDetails[1].uploadFile", "test.jpg", "image/jpeg",
				ArrayUtils.EMPTY_BYTE_ARRAY);
		atchFile.setFileDetails(Arrays.asList(new AtchFileDetailVO(file1), new AtchFileDetailVO(file2)));
	
		assertDoesNotThrow(()->{
			boardWithFiles.setAtchFile(atchFile);
			service.createNoticeBoard(boardWithFiles);
		});
		
		assertDoesNotThrow(()->{
			boardWithNoFiles.setAtchFile(null);
			service.createNoticeBoard(boardWithNoFiles);
		});
		
	}
	
	@AfterEach
	void afterEach() {
		assertDoesNotThrow(() -> {
			Optional.ofNullable(boardWithFiles)
					.map(NoticeBoardVO::getAtchFile)
					.map(AtchFileVO::getFileDetails)
					.map(List::stream).orElse(Stream.empty())
					.filter(fd -> fd.getFileStreCours() != null)
					.forEach(fd -> FileUtils.deleteQuietly(new File(fd.getFileStreCours())));
			Optional.ofNullable(boardWithNoFiles)
					.map(NoticeBoardVO::getAtchFile)
					.map(AtchFileVO::getFileDetails)
					.map(List::stream).orElse(Stream.empty())
					.filter(fd -> fd.getFileStreCours() != null)
					.forEach(fd -> FileUtils.deleteQuietly(new File(fd.getFileStreCours())));
		});
	}
	
//	@Test
//	void testCreateBoardNoFiles() throws IOException {
//	}
//
//	@Test
//	void testCreateBoardWithFiles() throws IOException {
//	}
	
//	@Test//통과
//	void testCreateNoticeBoard() {
//		NoticeBoardVO board = service.readNoticeBoard(1);
//		board.setNtcNm("뉴제목22");
//		service.createNoticeBoard(board);
//	
//		
//	}

	
//	@Test//통과
//	void testReadNoticeBoard() {
//		service.readNoticeBoard(1);
//	}
//
//	@Test//통과
//	void testReadNoticeBoardList() {
//		service.readNoticeBoardList();
//	}
//
//	@Test//통과
//	void readNoticeBoardListPaging() {
//		PaginationInfo<NoticeBoardVO> paging = new PaginationInfo<>();
//		paging.setCurrentPage(1);
//		SimpleCondition simpleCondition = new SimpleCondition();
//		simpleCondition.setSearchWord("은대");
//		paging.setSimpleCondition(simpleCondition);
//		assertDoesNotThrow(() -> service.readNoticeBoardListPaging(paging));
//	}

//	@Test//통과
//	void testModifyNoticeBoard() {
//		NoticeBoardVO board = service.readNoticeBoard(1);
//		board.setNtcNm("변경제목11111");
//		assertDoesNotThrow(()->{
//			service.modifyNoticeBoard(board);
//		});
//	}
//
//	@Test
//	void testRemoveNoticeBoard() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testDownload() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testRemoveFile() {
//		fail("Not yet implemented");
//	}

	/**
	 * 기존 파일 그룹이 없고, 신규 파일 그룹이 있는 경우.
	 */
	@Test
	void testModifyBoardCase2() {
		AtchFileVO addAtchFile = new AtchFileVO();
		MockMultipartFile file1 = new MockMultipartFile("atchFile.fileDetails[0].uploadFile", "test.jpg", "image/jpeg",
				ArrayUtils.EMPTY_BYTE_ARRAY);
		MockMultipartFile file2 = new MockMultipartFile("atchFile.fileDetails[1].uploadFile", "test.jpg", "image/jpeg",
				ArrayUtils.EMPTY_BYTE_ARRAY);
		addAtchFile.setFileDetails(Arrays.asList(new AtchFileDetailVO(file1), new AtchFileDetailVO(file2)));
		boardWithNoFiles.setAtchFile(addAtchFile);

		assertDoesNotThrow(() -> service.modifyNoticeBoard(boardWithNoFiles));
		boardWithNoFiles = service.readNoticeBoard(boardWithNoFiles.getNtcCd());
		assertEquals(2, boardWithNoFiles.getAtchFile().getFileDetails().size());
	}

	/**
	 * 기존 파일 그룹이 있고, 신규 파일 그룹이 없는 경우.
	 */
	@Test
	void testModifyBoardCase3() {
		boardWithFiles.setAtchFile(null);

		assertDoesNotThrow(() -> service.modifyNoticeBoard(boardWithFiles));
		boardWithFiles = service.readNoticeBoard(boardWithFiles.getNtcCd());
		assertEquals(2, boardWithFiles.getAtchFile().getFileDetails().size());
	}

	/**
	 * 기존 파일 그룹과 신규 파일 그룹이 모두 없는 경우.
	 */
	@Test
	void testModifyBoardCase4() {

		assertDoesNotThrow(() -> service.modifyNoticeBoard(boardWithNoFiles));

		assertNull(service.readNoticeBoard(boardWithNoFiles.getNtcCd()).getAtchFile());
	}
	
	
}
