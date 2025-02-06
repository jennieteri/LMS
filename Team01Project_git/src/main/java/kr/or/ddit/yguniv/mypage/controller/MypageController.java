package kr.or.ddit.yguniv.mypage.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.yguniv.attendcoeva.service.AttendCoevaService;
import kr.or.ddit.yguniv.commons.enumpkg.ServiceResult;
import kr.or.ddit.yguniv.commons.service.CommonCodeServiceImpl;
import kr.or.ddit.yguniv.file.ProfileImage;
import kr.or.ddit.yguniv.mypage.dao.MypageMapper;
import kr.or.ddit.yguniv.mypage.service.MypageService;
import kr.or.ddit.yguniv.vo.AttendeeVO;
import kr.or.ddit.yguniv.vo.PersonVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("mypage")
public class MypageController {

    public static final String MODELNAME = "my";

    @Autowired
    private MypageService service;

    @Autowired
    private AttendCoevaService coevaService;

    @Autowired
    private CommonCodeServiceImpl commonCodeService;

    @Autowired
    private MypageMapper mapper;

    @ModelAttribute(MODELNAME)
    public PersonVO person() {
        return new PersonVO();
    }

    @GetMapping("selectMypage/{id}")
    public String selectMypage(Principal principal, Model model, @PathVariable String id) throws IllegalAccessException {
        String personId = principal.getName();
        log.debug("Principal로부터 얻은 사용자 ID: {}", personId);
        if (!personId.equals(id)) {
            return "redirect:/login";
        }

        PersonVO person = service.selectPerson(id);
        model.addAttribute("my", person);

        boolean isFreshman = "SC06".equals(person.getStreCateCd());
        model.addAttribute("isFreshman", isFreshman);

        model.addAttribute("fromLogin", true); // 로그인 후 처음 진입 시 모달 표시

        return "mypage/myPageForm";
    }

    @GetMapping("{id}/UpdateMyPage")
    public String updateMypage(@PathVariable("id") String id, Model model) {
        log.info("사용자 아이디: {}", id);

        PersonVO person = service.selectPerson(id);
        if (person == null) {
            log.error("사용자 정보를 가져올 수 없습니다. ID: {}", id);
            model.addAttribute("errorMessage", "사용자 정보를 찾을 수 없습니다.");
            return "mypage/errorPage"; // 에러 페이지로 리다이렉트
        }

        log.info("조회된 사용자 정보: {}", person);

        boolean isFreshman = "SC06".equals(person.getStreCateCd());
        model.addAttribute("isFreshman", isFreshman);

        model.addAttribute(MODELNAME, person);
        model.addAttribute("fromLogin", false); // 수정 페이지에서는 모달 띄우지 않음

        return "mypage/myPageEdit";
    }

    @PostMapping("{id}/UpdateMyPage")
    public String updateMypage(
            @ModelAttribute(MODELNAME) PersonVO person,
            @PathVariable("id") String id,
            @RequestParam("confirmPswd") String confirmPswd,
            RedirectAttributes redirectAttributes,
            Model model) {

        log.info("수정 시작: ID {}, 수정 데이터: {}", id, person);

        if (person.getSmsRcptnAgreYn() == null) {
            person.setSmsRcptnAgreYn("N");
        }
        if (person.getEmlRcptnAgreYn() == null) {
            person.setEmlRcptnAgreYn("N");
        }

        // 비밀번호 확인
        if (person.getPswd() == null || confirmPswd == null || !person.getPswd().equals(confirmPswd)) {
            redirectAttributes.addFlashAttribute("errorMessage", "비밀번호가 일치하지 않거나 입력되지 않았습니다.");
            return "redirect:/mypage/" + id + "/UpdateMyPage";
        }

        try {
            if (person.getProflImage() != null && !person.getProflImage().isEmpty()) {
                String base64Image = ProfileImage.imgToBase64(person.getProflImage());
                person.setProflPhoto(base64Image);
                log.info("프로필 이미지 변환 완료");
            }

            ServiceResult result = service.updatePerson(person);
            if (result == ServiceResult.OK) {
                log.info("수정 성공");
                redirectAttributes.addFlashAttribute("successMessage", "수정 완료!");
            } else if (result == ServiceResult.INVALIDPASSWORD) {
                log.warn("비밀번호가 일치하지 않음");
                redirectAttributes.addFlashAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
            } else {
                log.warn("수정 실패");
                redirectAttributes.addFlashAttribute("errorMessage", "수정 중 오류 발생");
            }
        } catch (Exception e) {
            log.error("수정 중 오류 발생: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "수정 중 오류 발생: " + e.getMessage());
        }
        return "redirect:/mypage/selectMypage/" + id;
    }


    @GetMapping("{id}/auth")
    public String authenticateForm(@PathVariable String id, Model model) {
        model.addAttribute("id", id);
        return "mypage/authForm";
    }

    @PostMapping("{id}/auth")
    @ResponseBody
    public Map<String, Object> authenticateUser(@PathVariable String id, @RequestBody Map<String, String> request) {
        log.info("Authentication request received for ID: {}", id);

        Map<String, Object> response = new HashMap<>();
        String pswd = request.get("pswd");

        if ("java".equals(pswd)) { // 테스트용 비밀번호 검증
            response.put("success", true);
            response.put("message", "인증 성공");
        } else {
            response.put("success", false);
            response.put("message", "비밀번호가 일치하지 않습니다.");
        }
        return response;
    }

	//myPage용도 한 학생의 전체 학기 성적 조회
    @GetMapping("score/{stuId}")
    @ResponseBody
    public Map<String, Object> selectMyPageList(@PathVariable String stuId) {
       
    	Map<String, Object> map = new HashMap<>();
       
    	List<AttendeeVO> myPageLectureList = coevaService.selectMapageList(stuId);
        
    	map.put("myPageLectureList", myPageLectureList);
        
    	return map;
    }
}
