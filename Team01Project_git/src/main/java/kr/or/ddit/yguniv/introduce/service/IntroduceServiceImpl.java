package kr.or.ddit.yguniv.introduce.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import kr.or.ddit.yguniv.atch.service.AtchFileService;
import kr.or.ddit.yguniv.board.answerBoard.exception.BoardException;
import kr.or.ddit.yguniv.commons.exception.PKNotFoundException;
import kr.or.ddit.yguniv.introduce.dao.IntroduceMapper;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AtchFileDetailVO;
import kr.or.ddit.yguniv.vo.CertificateVO;
import kr.or.ddit.yguniv.vo.IntroduceVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class IntroduceServiceImpl implements IntroduceService {
	@Autowired
	private final IntroduceMapper mapper;
	private final AtchFileService atchFileService;
	
	
	@Value("#{dirInfo.fsaveDir}")
	private Resource saveFolderRes;
	private File saveFolder;

	@PostConstruct
	public void init() throws IOException {
		this.saveFolder = saveFolderRes.getFile();
	}
	

	@Override
	public IntroduceVO updateintroduce(IntroduceVO introduce) {
		int updateCount = mapper.updateintroduce(introduce); 
        if (updateCount > 0) {
            // 업데이트 성공 시 수정된 데이터 다시 조회
            return mapper.selectintroduce(introduce.getIntCd());
        }
        return null;
	}

	@Override
	public IntroduceVO selectMyIntroduce(int intCd) {
	    IntroduceVO introduce = mapper.selectMyIntroduce(intCd);
	    if (introduce == null) {
	        throw new PKNotFoundException("해당 자기소개서를 찾을 수 없습니다.");
	    }
	    return introduce;
	}

	@Override
	public IntroduceVO selectintroduce(int intCd) {
		IntroduceVO introduce =mapper.selectintroduce(intCd);
		return introduce;
	}
	
	@Override
	public IntroduceVO selectEditedIntroduce(String stuId) {
		return mapper.selectEditedIntroduce(stuId);
	}

	//교직원용
	@Override
	public List<IntroduceVO> selectintroduceListPaging(PaginationInfo<IntroduceVO> paging) {
		paging.setTotalRecord(mapper.selectTotalRecord(paging));
		List<IntroduceVO>introduceList = mapper.selectintroduceListPaging(paging);
		return introduceList;
	}
	
	//학생용
	@Override
	public List<IntroduceVO> selectIntroduceByUserId(String stuId,PaginationInfo<IntroduceVO> paging) {
		paging.setTotalRecord(mapper.selectTotalRecord(paging));
		List<IntroduceVO>myintroduce = mapper.selectIntroduceByUserId(stuId,paging);
		return myintroduce;
	}

	@Override
	public void deleteintroduce(String stuId) {
		// TODO Auto-generated method stub

	}

	@Override
	public int selectTotalRecord(PaginationInfo<IntroduceVO> paging) {
		// TODO Auto-generated method stub
		return 0;
	}


	// 2024-10-10 => 20241010 / 09:00 => 0900
	   public CertificateVO dateAndTimeFormat(CertificateVO certVo) {
		   certVo.setCertDate(certVo.getCertDate().replace("-", ""));
	      return certVo;
	   }

	   /**
	     * 자기소개서 저장
	     */
	    @Override
	    public void insertIntroduce(IntroduceVO introduce) {
	        mapper.insertIntroduce(introduce);
	    }
	    
	    
		@Override
		public AtchFileDetailVO download(int atchFileId, int fileSn) {
			return Optional.ofNullable(atchFileService.readAtchFileDetail(atchFileId, fileSn, saveFolder))
					.filter(fd -> fd.getSavedFile().exists())
					.orElseThrow(() -> new BoardException(String.format("[%d, %d]해당 파일이 없음.", atchFileId, fileSn)));
		}

	    /**
	     * 자격증 저장
	     */
	    @Override
	    public void insertCertificate(CertificateVO certificate) {
	        // 첨부파일 처리
	        Integer atchFileId = Optional.ofNullable(certificate.getAtchFile())
	            .filter(atchFile -> atchFile != null && !CollectionUtils.isEmpty(atchFile.getFileDetails()))
	            .map(atchFile -> {
	                atchFileService.createAtchFile(atchFile, saveFolder);
	                return atchFile.getAtchFileId();
	            }).orElse(null);

	        certificate.setAtchFileId(atchFileId); // 파일 ID 설정
	        certificate.setCertDate(certificate.getCertDate().replace("-", "")); // 날짜 형식 변환

	        // 자격증 저장
	        mapper.insertCertificate(certificate);
	    }


		@Override
		public void insertIntroduceCertificate(IntroduceVO introduce) {
			// TODO Auto-generated method stub
			
		}

		
	}




