package kr.or.ddit.yguniv.commons.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.yguniv.commons.service.DateCheckServiceImpl;
import kr.or.ddit.yguniv.vo.NoticeBoardVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("dateCheck")
public class DateCheckController {

    private final DateCheckServiceImpl service;

    @GetMapping("{cocoCd}")
    public ResponseEntity<Object> getDate(@PathVariable String cocoCd) {
        log.info("cocoCd: {}", cocoCd);

        NoticeBoardVO notiVo = service.getDate(cocoCd);
        boolean tf = service.dateCheck(notiVo);

        // 날짜 범위가 유효한 경우 200 OK 반환
        if (tf) {
            return ResponseEntity.ok().body("날짜 범위 확인 성공");
        }

        // 날짜 범위가 유효하지 않은 경우 400 Bad Request 반환
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 날짜가 아닙니다.");
    }
}
	
	

























