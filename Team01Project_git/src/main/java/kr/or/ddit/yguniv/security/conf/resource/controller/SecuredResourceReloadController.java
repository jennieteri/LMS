package kr.or.ddit.yguniv.security.conf.resource.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.yguniv.security.authorize.ReloadableAuthorizationManager;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SecuredResourceReloadController {
	private final ReloadableAuthorizationManager reloadableManager;

	@RequestMapping("/resource/reload")
	@ResponseBody
	public ResponseEntity<Object> reload() {
	HttpStatus status = HttpStatus.OK;
	Map<String, String> body = new HashMap<>();
	body.put("message", "권한이 성공적으로 업데이트되었습니다.");

			reloadableManager.reload();
		return ResponseEntity.status(status).body(body);
	}
}
