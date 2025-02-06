package kr.or.ddit.yguniv.mypage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.mypage.dao.MainUIMapper;
import kr.or.ddit.yguniv.vo.ActiveModuleVO;
import kr.or.ddit.yguniv.vo.LectureVO;
import kr.or.ddit.yguniv.vo.ModuleVO;

@Service
public class MainUIService {
	@Autowired
	private MainUIMapper uiMapper;
	
	public List<ActiveModuleVO> myModule(String id) {
		return uiMapper.myModule(id);
	}
	
	public List<LectureVO> schedule(String id){
		return uiMapper.schedule(id);
	}

	public void saveModule(String id, List<ActiveModuleVO> modList) {
		uiMapper.deleteModule(id);
		if(!modList.isEmpty()) {
			uiMapper.saveModule(id, modList);
		}
	}

	public List<ModuleVO> moduleList() {
		return uiMapper.moduleList();
	}

}
