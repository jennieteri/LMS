package kr.or.ddit.yguniv.mypage.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.yguniv.commons.service.CommonCodeServiceImpl;
import kr.or.ddit.yguniv.mypage.service.MainUIService;
import kr.or.ddit.yguniv.notification.dao.NotificationMapper;
import kr.or.ddit.yguniv.vo.ActiveModuleVO;

@Controller
@RequestMapping("mainui")
public class MainUIController {
	@Autowired
	private MainUIService uiService;
	@Autowired
	private CommonCodeServiceImpl cocoService;
	@Autowired
	private NotificationMapper mapper;
	
	@GetMapping("my")
	@ResponseBody
	public List<ActiveModuleVO> myModule(Authentication authentication){
		return uiService.myModule(authentication.getName());
	}
	
	@PostMapping("my")
	@ResponseBody
	public void saveModule(Authentication authentication, @RequestBody List<ActiveModuleVO> modList) {
		uiService.saveModule(authentication.getName(), modList);
	}
	
	@GetMapping("schedulejsp")
	public String scheduleJsp(Authentication authentication, Model model) {
		model.addAttribute("lectList", uiService.schedule(authentication.getName()));
		model.addAttribute("dotwList", cocoService.getCodeList("DOTW"));
		model.addAttribute("etimeList", cocoService.getEducationTimeTableCodeList());
		return "/moduleUI/schedule";
	}
	
	@GetMapping("notifyjsp")
	public String notifyJsp(Authentication authentication, Model model) {
		model.addAttribute("notifyList", mapper.selectModuleNotificationList(authentication.getName()));
		return "/moduleUI/notify";
	}
}
