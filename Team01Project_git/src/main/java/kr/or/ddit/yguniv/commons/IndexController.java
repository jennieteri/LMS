package kr.or.ddit.yguniv.commons;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.yguniv.mypage.service.MainUIService;


@Controller
public class IndexController{
	@Autowired
	private MainUIService uiService;
	
	@RequestMapping("/index.do")
	public String index(Model model) {
		model.addAttribute("modList", uiService.moduleList());
		return "index";
	}
		
}





