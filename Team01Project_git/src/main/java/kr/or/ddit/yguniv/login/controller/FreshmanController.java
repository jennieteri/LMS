package kr.or.ddit.yguniv.login.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FreshmanController {

    @PostMapping("/checkFreshman")
    public ResponseEntity<Map<String, Object>> checkFreshman(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        Map<String, Object> response = new HashMap<>();

        if (userId == null || userId.isEmpty()) {
            response.put("error", "userId가 비어 있습니다.");
            return ResponseEntity.badRequest().body(response);
        }

        // 신입생 여부 확인 로직
        boolean isFreshman = "SC06".equals(userId); // 예제
        response.put("isFreshman", isFreshman);

        return ResponseEntity.ok(response);
    }
}

