package kr.or.ddit.yguniv.absenceCertificateReceipt;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.yguniv.absencecertificatereceipt.dao.AbsenceCertificateReceiptMapper;
import kr.or.ddit.yguniv.annotation.RootContextWebConfig;
import kr.or.ddit.yguniv.vo.AbsenceCertificateReceiptVO;
import kr.or.ddit.yguniv.vo.AttendanceVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RootContextWebConfig
@Transactional
class AbsenceCertificateReceiptTest {
	
	@Autowired
	AbsenceCertificateReceiptMapper mapper;
	
//	@Test
//	void test() {
//		List<AbsenceCertificateReceiptVO> result = mapper.updateAbsenceCertificateReceipt();
//		assertNotNull(result);
//		log.info("체크 : {}", result.toString());
//	}
	
//	@Test
//	void test() {
//		List<AbsenceCertificateReceiptVO> result = mapper.selectAbsenceCertificateReceiptList();
//		assertNotNull(result);
//		log.info("체크 : {}", result.toString());
//	}

}
