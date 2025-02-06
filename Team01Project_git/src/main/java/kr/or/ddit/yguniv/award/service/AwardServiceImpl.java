package kr.or.ddit.yguniv.award.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.award.dao.AwardMapper;
import kr.or.ddit.yguniv.board.answerBoard.exception.BoardException;
import kr.or.ddit.yguniv.paging.PaginationInfo;
import kr.or.ddit.yguniv.vo.AwardVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwardServiceImpl implements AwardService {

	
	private final AwardMapper mapper;
	
	
	@Override
	public List<AwardVO> awardList() {
		
		return mapper.selectList();
	}

	@Override
	public AwardVO selectAward(String awardCd) {
		
		AwardVO system = mapper.selectAward(awardCd);
		
		
		if(system == null ) {
			throw new BoardException(String.format("%s 번 글이 없음", awardCd));
		}else {
           log.info("system 있니 ? ",system);
        }
		
		
		return system;
	}

	public void insertAward (final AwardVO award) {
		mapper.insertAward(award);
	}
	
	@Override
	public void updateAward(AwardVO award) {
		mapper.updateAward(award);
		

	}

	@Override
	public void deleteAward(String awardCd) {
		
		mapper.deleteAward(awardCd);

		

	}

	@Override
	public List<AwardVO> selectAdminAwardList() {
		return mapper.selectList();
	}

	@Override
	public List<AwardVO> selectAdminAwardList(PaginationInfo<AwardVO> paginationInfo) {
		
		paginationInfo.setTotalRecord(mapper.selectTotalRecord(paginationInfo));
		
		List<AwardVO> awardList = mapper.selectAdminAwardList(paginationInfo);
		
		return awardList;
	}

	@Override
	public int selectTotalRecord(PaginationInfo<AwardVO> paginationInfo) {
		
		return 0;
	}

	@Override
	public List<AwardVO> selectAdminAwardSearch(PaginationInfo<AwardVO> paginationInfo) {
		if(paginationInfo != null) {
			int totalRecord = mapper.selectTotalRecord(paginationInfo);
			paginationInfo.setTotalRecord(totalRecord);
		}
		return mapper.selectAdminAwardList(paginationInfo);
	}

}
