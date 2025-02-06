package kr.or.ddit.yguniv.login.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.yguniv.login.service.PersonFindLoginService;
import kr.or.ddit.yguniv.vo.PersonVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("findLogin")
public class FindMyLoginController {
	
    public static final String MODELNAME = "person";

    @Autowired
    public PersonFindLoginService service;

    @ModelAttribute(MODELNAME)
    public PersonVO person() {
        return new PersonVO();
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> selectFindForm(
            @RequestParam String nm,
            @RequestParam String brdt
    ) {
        log.info("요청 받은 이름: {}, 생년월일: {}", nm, brdt);
        Map<String, Object> response = new HashMap<>();
        try {
            // 서비스 호출
            String id = service.selectFindForm(nm, brdt);
            log.info("조회 결과 학번: {}", id);

            if (id != null) {
                response.put("id", id);
                response.put("message", "학번 조회 성공");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "해당하는 학번을 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (IllegalArgumentException e) {
            log.error("잘못된 요청: {}", e.getMessage());
            response.put("message", "잘못된 요청입니다.");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            log.error("서버 내부 오류 발생", e);
            response.put("message", "서버 내부 오류가 발생했습니다.");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }







}
